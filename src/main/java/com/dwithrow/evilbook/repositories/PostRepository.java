package com.dwithrow.evilbook.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dwithrow.evilbook.models.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	Optional<Post> findById(Long id);
	List<Post> findAll();
	@Query("select p from Post p where user_id = ?1 order by created_at desc")
	List<Post> findByPoster(Long user_id);
	@Query("select p from Post p order by created_at desc")
	List<Post> findAllByNewest();
}