package com.dwithrow.evilbook.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dwithrow.evilbook.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
	Optional<Message> findById(Long id);
	List<Message> findAll();
	@Query("Select m from Message m where sender_id = ?1 order by created_at desc")
	List<Message> findBySender(Long sender_id);
	@Query("Select m from Message m where recipient_id = ?1 order by created_at desc")
	List<Message> findByRecipient(Long recipient_id);
	@Query("select count(m) from Message m where recipient_id = ?1 and isRead = false")
	int countUnread(Long recipient_id);
}