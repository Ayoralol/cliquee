package cliquee.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cliquee.backend.model.User;
import cliquee.backend.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
}
