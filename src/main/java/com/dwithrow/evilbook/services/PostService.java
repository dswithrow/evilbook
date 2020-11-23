package com.dwithrow.evilbook.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dwithrow.evilbook.models.Post;
import com.dwithrow.evilbook.repositories.PostRepository;

@Service
public class PostService {
	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Post createPost(Post post) {
		return postRepository.save(post);
	}
	
	public Post findPostById(Long post_id) {
		Optional<Post> post = postRepository.findById(post_id);
		if (post.isPresent()) {
			return post.get();
		}
		return null;
	}
	
	public List<Post> findAllPosts() {
		return postRepository.findAll();
	}
	
	public List<Post> findAllPostsNewestFirst() {
		return postRepository.findAllByNewest();
	}
	
	public List<Post> findPostByPoster(Long user_id) {
		return postRepository.findByPoster(user_id);
	}
}
