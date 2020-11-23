package com.dwithrow.evilbook.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.dwithrow.evilbook.models.Bio;
import com.dwithrow.evilbook.models.Comment;
import com.dwithrow.evilbook.models.Message;
import com.dwithrow.evilbook.models.Post;
import com.dwithrow.evilbook.models.User;
import com.dwithrow.evilbook.services.BioService;
import com.dwithrow.evilbook.services.CommentService;
import com.dwithrow.evilbook.services.MessageService;
import com.dwithrow.evilbook.services.PostService;
import com.dwithrow.evilbook.services.UserService;

@Controller
public class HomeController {
	private final UserService userS;
	private final BioService bioS;
	private final PostService postS;
	private final CommentService commS;
	private final MessageService messS;
	
	public HomeController(UserService userS, BioService bioS, PostService postS,
						  CommentService commS, MessageService messS) {
		this.userS = userS;
		this.bioS = bioS;
		this.postS = postS;
		this.commS = commS;
		this.messS = messS;
	}
	
	/*
	 * Login and Registration
	 */
	@RequestMapping("/login")
	public String login(@ModelAttribute("user") User user) {
		return "login.jsp";
	}
	@PostMapping("/login")
	public String loginUser(@ModelAttribute("user") User user, @RequestParam("alias") String alias,
							@RequestParam("password") String password, Model model, HttpSession session) {
		User loggedUser = userS.authenticateUser(alias, password);
		if (loggedUser != null) {
			session.setAttribute("user_id", loggedUser.getId());
			return "redirect:/";
		}
		model.addAttribute("error", "Email and password combo does not match records.");
		return "login.jsp";
	}
	@PostMapping("/registration")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			return "login.jsp";
		}
		user.setBio(bioS.createBio(new Bio()));
		user.getBio().setDisplayName(user.getUsername());
		User newUser = userS.createUser(user);
		session.setAttribute("user_id", newUser.getId());
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user_id");
		return "redirect:/login";
	}
	
	/*
	 * Home feed
	 */
	@RequestMapping("/") // Home Page
	public String homePage(@ModelAttribute("post") Post post, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", userS.findUserById(userId));
		model.addAttribute("posts", postS.findAllPostsNewestFirst());
		model.addAttribute("newMessages", messS.countUnread(userId));
		return "home.jsp";
	}
	@PostMapping("/") // Make a new post
	public String createPost(@Valid @ModelAttribute("post") Post post, BindingResult result,
							 Model model, HttpSession session) {
		if (result.hasErrors()) {
			return homePage(post, model, session);
		}
		post.setUser(userS.findUserById((Long) session.getAttribute("user_id")));
		postS.createPost(post);
		return "redirect:/";
	}
	@PostMapping("/comment/{post_id}") // Add a comment to a post
	public String createComment(@PathVariable("post_id") Long postId, @RequestParam("body") String body,
								Model model, HttpSession session) {
		if (body.trim().equals("")) {
			model.addAttribute("commentError", "Comments can not be blank.");
			return homePage(new Post(), model, session) + "#" + postId.toString();
		}
		Post post = postS.findPostById(postId);
		if (post == null) { return "redirect:/"; }
		Comment comment = new Comment();
		comment.setPost(post);
		comment.setBody(body);
		comment.setUser(userS.findUserById((Long) session.getAttribute("user_id")));
		commS.createComment(comment);
		return "redirect:/#" + postId.toString();
	}
	
	/*
	 * Profile Information
	 */
	@RequestMapping("/profile/{user_id}")
	public String viewProfile(@PathVariable("user_id") Long userId, Model model, HttpSession session) {
		Long loggedUser = (Long) session.getAttribute("user_id");
		User profiledUser = userS.findUserById(userId);
		if (loggedUser == null || profiledUser == null) {
			return "redirect:/";
		}
		model.addAttribute("profile", profiledUser);
		model.addAttribute("user", userS.findUserById(loggedUser));
		model.addAttribute("viewingOther", !userId.equals(loggedUser));
		return "profile.jsp";
	}
	@RequestMapping("/profile/{user_id}/edit")
	public String editProfile(@PathVariable("user_id") Long userId, @ModelAttribute("bio") Bio bio,
							  Model model, HttpSession session) {
		if (!userId.equals(session.getAttribute("user_id"))) {
			return "redirect:/profile/" + userId.toString();
		}
		User user = userS.findUserById(userId);
		model.addAttribute("user", user);
		return "editProfile.jsp";
	}
	@PostMapping("/profile/{user_id}/edit")
	public String editProfileValidate(@PathVariable("user_id") Long userId, @Valid @ModelAttribute("bio") Bio bio,
			  						  BindingResult result, Model model, HttpSession session) {
		if (result.hasErrors()) {
			return editProfile(userId, bio, model, session);
		}
		Bio updateBio = bioS.findBioByUserId(userId);
		updateBio.setDisplayName(bio.getDisplayName());
		updateBio.setAbout(bio.getAbout());
		updateBio.setOccupation(bio.getOccupation());
		updateBio.setNemesis(bio.getNemesis());
		bioS.updateBio(updateBio);
		return "redirect:/profile/" +  userId.toString();
	}
	
	/*
	 * Inbox and messaging
	 */
	@RequestMapping("/mail")
	public String viewInbox(Model model, HttpSession session) {
		Long loggedUser = (Long) session.getAttribute("user_id");
		if (loggedUser == null) { return "redirect:/login"; }
		
		model.addAttribute("messages", messS.findMessagesByRecipient(userS.findUserById(loggedUser).getId()));
		model.addAttribute("user", userS.findUserById(loggedUser));
		return "inbox.jsp";
	}
	@RequestMapping("/mail/out")
	public String viewOutbox(Model model, HttpSession session) {
		Long loggedUser = (Long) session.getAttribute("user_id");
		if (loggedUser == null) { return "redirect:/login"; }
		model.addAttribute("messages", messS.findMessagesBySender(userS.findUserById(loggedUser).getId()));
		model.addAttribute("user", userS.findUserById(loggedUser));
		return "outbox.jsp";
	}
	@RequestMapping("/mail/{message_id}")
	public String viewMessage(@PathVariable("message_id") Long messageId, Model model, HttpSession session) {
		Long loggedUser = (Long) session.getAttribute("user_id");
		if (loggedUser == null) { return "redirect:/login"; }
		Message message = messS.findMessageById(messageId);
		if (message.getRecipient().getId() == loggedUser ||
			message.getSender().getId() == loggedUser) {
			model.addAttribute("message", message);
			model.addAttribute("isRecipient", message.getRecipient().getId() == loggedUser);
			model.addAttribute("user", userS.findUserById(loggedUser));
			messS.markAsRead(message);
			return "message.jsp";
		}
		return "redirect:/mail";
	}
	@RequestMapping("/mail/new/{recipient_id}")
	public String createMessage(@PathVariable("recipient_id") Long recipientId, @ModelAttribute("message") Message message,
								Model model, HttpSession session) {
		Long loggedUser = (Long) session.getAttribute("user_id");
		if (loggedUser == null || loggedUser.equals(recipientId)) {
			return "redirect:/mail";
		}
		model.addAttribute("recipient", userS.findUserById(recipientId));
		model.addAttribute("user", userS.findUserById(loggedUser));
		return "createMessage.jsp";
	}
	@PostMapping("/mail/send/{recipient_id}")
	public String sendMessage(@PathVariable("recipient_id") Long recipientId, @Valid @ModelAttribute("message") Message message,
							  BindingResult result, Model model, HttpSession session) {
		User recipient = userS.findUserById(recipientId);
		if(result.hasErrors()) {
			model.addAttribute("recipient", recipient);
			return "createMessage.jsp";
		}
		if (message.getSubject().trim().equals("")) {
			message.setSubject("No Subject");
		}
		message.setRecipient(recipient);
		message.setSender(userS.findUserById((Long) session.getAttribute("user_id")));
		messS.createMessage(message);
		return "redirect:/mail";
	}
	@RequestMapping("/mail/reply/{message_id}")
	public String replyMessage(@PathVariable("message_id") Long messageId, @ModelAttribute("message") Message message,
								Model model, HttpSession session) {
		Long loggedUser = (Long) session.getAttribute("user_id");
		Message origMessage = messS.findMessageById(messageId);
		if (loggedUser == null || !origMessage.getRecipient().getId().equals(loggedUser)) {
			return "redirect:/mail";
		}
		model.addAttribute("replyingTo", origMessage);; 
		model.addAttribute("recipient", origMessage.getSender());
		model.addAttribute("user", userS.findUserById(loggedUser));
		return "createMessage.jsp";
	}
	
	/*
	 * 404 Redirect to home
	 */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404() {
        return "redirect:/";
    }
}
