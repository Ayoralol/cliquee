package cliquee.backend.service;

import cliquee.backend.model.User;
import cliquee.backend.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public List<User> getAllUsers(UUID userId) {
    return userRepository.findAllExcludingBlocked(userId);
  }

  public Optional<User> getUserById(UUID id, UUID userId) {
    return userRepository.findByIdExcludingBlocked(id, userId);
  }

  public Optional<User> getUserByUsername(String username, UUID userId) {
    return userRepository.findByUsernameExcludingBlocked(username, userId);
  }

  public Optional<User> getUserByEmail(String email, UUID userId) {
    return userRepository.findByEmailExcludingBlocked(email, userId);
  }

  public List<User> searchUsers(String username, UUID userId) {
    return userRepository.searchUsersExcludingBlocked(username, userId);
  }

  public User createUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User updateUser(UUID id, User userDetails) {
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

  public void deleteUser(UUID id) {
    userRepository.deleteById(id);
  }

  public void changePassword(UUID id, String newPassword) {
    userRepository
      .findById(id)
      .ifPresent(user -> {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
      });
  }
}
