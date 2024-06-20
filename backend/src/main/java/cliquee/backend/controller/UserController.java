package cliquee.backend.controller;

import cliquee.backend.model.Block;
import cliquee.backend.model.User;
import cliquee.backend.service.BlockService;
import cliquee.backend.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @GetMapping("/{userId}")
  public ResponseEntity<User> getUserById(
    @PathVariable UUID userId,
    @RequestParam UUID currentUserId
  ) {
    Optional<User> user = userService.getUserById(userId, currentUserId);
    return user
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<User> getUserByUsername(
    @PathVariable String username,
    @RequestParam UUID currentUserId
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
    @RequestParam UUID currentUserId
  ) {
    Optional<User> user = userService.getUserByEmail(email, currentUserId);
    return user
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/search")
  public ResponseEntity<List<User>> searchUsers(
    @RequestParam UUID currentUserId,
    @RequestParam String keyword
  ) {
    List<User> users = userService.searchUsers(currentUserId, keyword);
    return ResponseEntity.ok(users);
  }

  @PutMapping("/update/{currentUserId}")
  public ResponseEntity<User> updateUser(
    @PathVariable UUID currentUserId,
    @RequestBody User userDetails
  ) {
    User user = userService.updateUser(currentUserId, userDetails);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/create")
  public ResponseEntity<User> createUser(@RequestBody User userDetails) {
    User newUser = userService.createUser(userDetails);
    return ResponseEntity.ok(newUser);
  }

  @DeleteMapping("/delete/{currentUserId}")
  public ResponseEntity<?> deleteUser(@PathVariable UUID currentUserId) {
    userService.deleteUser(currentUserId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/change-password/{currentUserId}")
  public ResponseEntity<?> changePassword(
    @PathVariable UUID currentUserId,
    @RequestBody String oldPassword,
    @RequestBody String newPassword
  ) {
    userService.changePassword(currentUserId, oldPassword, newPassword);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/block/{blocked_id}")
  public ResponseEntity<Block> blockUser(
    @PathVariable UUID blockedId,
    @RequestParam UUID currentUserId
  ) {
    try {
      Block block = blockService.blockUser(blockedId, currentUserId);
      return ResponseEntity.ok(block);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @DeleteMapping("/unblock/{blocked_id}")
  public ResponseEntity<Void> unblockUser(
    @PathVariable UUID blockedId,
    @RequestParam UUID currentUserId
  ) {
    try {
      blockService.unblockUser(blockedId, currentUserId);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/blocked/{currentUserId}")
  public ResponseEntity<List<Block>> getBlockedUsers(
    @PathVariable UUID currentUserId
  ) {
    List<Block> blockedUsers = blockService.getBlockedUsers(currentUserId);
    return ResponseEntity.ok(blockedUsers);
  }
}
