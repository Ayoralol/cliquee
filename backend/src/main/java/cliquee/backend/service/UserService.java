package cliquee.backend.service;

import cliquee.backend.model.User;
import cliquee.backend.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User createUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User updateUser(Long id, User userDetails) {
    return userRepository
      .findById(id)
      .map(user -> {
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFirst_name(userDetails.getFirst_name());
        user.setLast_name(userDetails.getLast_name());
        user.setRole(userDetails.getRole());
        user.setPrivacy(userDetails.getPrivacy());
        user.setUpdatedAt(userDetails.getUpdatedAt());
        return userRepository.save(user);
      })
      .orElseGet(() -> {
        userDetails.setId(id);
        return userRepository.save(userDetails);
      });
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  public void changePassword(Long id, String newPassword) {
    userRepository
      .findById(id)
      .ifPresent(user -> {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
      });
  }
}
