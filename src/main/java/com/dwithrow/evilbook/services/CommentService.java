package com.dwithrow.evilbook.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dwithrow.evilbook.models.Comment;
import com.dwithrow.evilbook.repositories.CommentRepository;

@Service
public class CommentService {
	private final CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	public Comment createComment(Comment comment) {
		return commentRepository.save(comment);
	}
	
	public Comment findCommentById(Long comment_id) {
		Optional<Comment> comment = commentRepository.findById(comment_id);
		if(comment.isPresent()) {
			return comment.get();
		}
		return null;
	}
	
	public List<Comment> findAllComments() {
		return commentRepository.findAll();
	}

	public List<Comment> findCommentsByPost(Long post_id) {
		return commentRepository.findByPost(post_id);
	}
}
