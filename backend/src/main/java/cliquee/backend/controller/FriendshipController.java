package cliquee.backend.controller;

import cliquee.backend.model.FriendRequest;
import cliquee.backend.service.FriendshipService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

  private final FriendshipService friendshipService;

  public FriendshipController(FriendshipService friendshipService) {
    this.friendshipService = friendshipService;
  }

  @GetMapping("/{currentUserId}")
  public ResponseEntity<List<UUID>> getFriends(
    @PathVariable UUID currentUserId
  ) {
    List<UUID> friends = friendshipService.getFriendIds(currentUserId);
    return ResponseEntity.ok(friends);
  }

  @PostMapping("/request/{friendId}")
  public ResponseEntity<FriendRequest> sendFriendRequest(
    @PathVariable UUID friendId,
    @RequestParam UUID currentUserId
  ) {
    try {
      FriendRequest friendRequest = friendshipService.sendFriendRequest(
        friendId,
        currentUserId
      );
      return ResponseEntity.ok(friendRequest);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @GetMapping("/requests/{currentUserId}")
  public ResponseEntity<List<FriendRequest>> getFriendRequests(
    @PathVariable UUID currentUserId
  ) {
    List<FriendRequest> friendRequests = friendshipService.getFriendRequests(
      currentUserId
    );
    return ResponseEntity.ok(friendRequests);
  }

  @PostMapping("/requests/accept/{requestId}")
  public ResponseEntity<FriendRequest> acceptFriendRequest(
    @PathVariable UUID requestId,
    @RequestParam UUID currentUserId
  ) {
    try {
      FriendRequest friendRequest = friendshipService.acceptFriendRequest(
        requestId,
        currentUserId
      );
      return ResponseEntity.ok(friendRequest);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @PostMapping("/requests/deny/{requestId}")
  public ResponseEntity<FriendRequest> denyFriendRequest(
    @PathVariable UUID requestId,
    @RequestParam UUID currentUserId
  ) {
    try {
      FriendRequest friendRequest = friendshipService.denyFriendRequest(
        requestId,
        currentUserId
      );
      return ResponseEntity.ok(friendRequest);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
