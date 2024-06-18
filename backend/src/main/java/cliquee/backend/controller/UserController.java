package cliquee.backend.controller;

import cliquee.backend.model.Block;
import cliquee.backend.model.User;
import cliquee.backend.service.BlockService;
import cliquee.backend.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private BlockService blockService;

  @GetMapping("/{id}/all")
  public List<User> getAllUsers(@PathVariable Long id) {
    return userService.getAllUsers(id);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(
    @PathVariable Long id,
    @RequestParam Long currentUserId
  ) {
    Optional<User> user = userService.getUserById(id, currentUserId);
    return user
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<User> getUserByUsername(
    @PathVariable String username,
    @RequestParam Long currentUserId
  ) {
    Optional<User> user = userService.getUserByUsername(
      username,
      currentUserId
    );
    return user
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<User> getUserByEmail(
    @PathVariable String email,
    @RequestParam Long currentUserId
  ) {
    Optional<User> user = userService.getUserByEmail(email, currentUserId);
    return user
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/search/{keyword}")
  public ResponseEntity<List<User>> searchUsers(
    @PathVariable String keyword,
    @RequestParam Long currentUserId
  ) {
    List<User> users = userService.searchUsers(keyword, currentUserId);
    return ResponseEntity.ok(users);
  }

  @PutMapping("/{id}/update")
  public ResponseEntity<User> updateUser(
    @PathVariable Long id,
    @RequestBody User userDetails
  ) {
    User user = userService.updateUser(id, userDetails);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/create")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    User newUser = userService.createUser(user);
    return ResponseEntity.ok(newUser);
  }

  @PostMapping("/{id}/delete")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/change-password")
  public ResponseEntity<?> changePassword(
    @PathVariable Long id,
    @RequestBody String newPassword
  ) {
    userService.changePassword(id, newPassword);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/block/{blocked_id}")
  public ResponseEntity<Block> blockUser(
    @PathVariable Long id,
    @PathVariable Long blockedId
  ) {
    try {
      Block block = blockService.blockUser(id, blockedId);
      return ResponseEntity.ok(block);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @PostMapping("/{id}/unblock/{blocked_id}")
  public ResponseEntity<Void> unblockUser(
    @PathVariable Long id,
    @PathVariable Long blockedId
  ) {
    try {
      blockService.unblockUser(id, blockedId);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{id}/blocked")
  public ResponseEntity<List<Block>> getBlockedUsers(@PathVariable Long id) {
    List<Block> blockedUsers = blockService.getBlockedUsers(id);
    return ResponseEntity.ok(blockedUsers);
  }
}
