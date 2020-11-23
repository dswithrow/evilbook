package com.dwithrow.evilbook.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dwithrow.evilbook.models.Message;
import com.dwithrow.evilbook.repositories.MessageRepository;

@Service
public class MessageService {
	private final MessageRepository messageRepository;
	
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public Message createMessage(Message message) {
		return messageRepository.save(message);
	}
	
	public Message findMessageById(Long message_id) {
		Optional<Message> message = messageRepository.findById(message_id);
		if(message.isPresent()) {
			return message.get();
		}
		return null;
	}
	
	public List<Message> findAllMessages(){
		return messageRepository.findAll();
	}
	
	public List<Message> findMessagesBySender(Long sender_id){
		return messageRepository.findBySender(sender_id);
	}
	
	public List<Message> findMessagesByRecipient(Long recipient_id) {
		return messageRepository.findByRecipient(recipient_id);
	}
	
	public int countUnread(Long recipient_id) {
		return messageRepository.countUnread(recipient_id);
	}

	public Message markAsRead(Message message) {
		message.markAsRead();
		return messageRepository.save(message);
	}
}
