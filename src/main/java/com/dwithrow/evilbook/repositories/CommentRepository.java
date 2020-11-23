package com.dwithrow.evilbook.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dwithrow.evilbook.models.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>{
	Optional<Comment> findById(Long id);
	List<Comment> findAll();
	@Query("select c from Comment c where post_id = ?1")
	List<Comment> findByPost(Long post_id);
}
