package com.dwithrow.evilbook.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.dwithrow.evilbook.models.User;
import com.dwithrow.evilbook.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User createUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
    }
    
    public User findByUsername(String username) {
    	return userRepository.findByUsername(username);
    }

	public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
	
	public List<User> allUsers(){
		return userRepository.findAll();
	}
    
    public User findUserById(Long id) {
    	Optional<User> user = userRepository.findById(id);
    	
    	if(user.isPresent()) {
            return user.get();
    	} else {
    	    return null;
    	}
    }
    
    public User authenticateUser(String alias, String password) {
        User user = alias.contains("@") ? userRepository.findByEmail(alias) : userRepository.findByUsername(alias);
        if(user == null) {
            return null;
        } else {
            if(BCrypt.checkpw(password, user.getPassword())) {
                return user;
            } else {
                return null;
            }
        }
    }
}
