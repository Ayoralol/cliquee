package cliquee.backend.controller;

import cliquee.backend.model.FriendRequest;
import cliquee.backend.service.FriendshipService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

  @Autowired
  private FriendshipService friendshipService;

  @GetMapping("/{userid}")
  public ResponseEntity<List<UUID>> getFriends(@PathVariable UUID userId) {
    List<UUID> friends = friendshipService.getFriendIds(userId);
    return ResponseEntity.ok(friends);
  }

  @PostMapping("/{userId}/request/{friendId}")
  public ResponseEntity<FriendRequest> sendFriendRequest(
    @PathVariable UUID userId,
    @PathVariable UUID friendId
  ) {
    try {
      FriendRequest friendRequest = friendshipService.sendFriendRequest(
        userId,
        friendId
      );
      return ResponseEntity.ok(friendRequest);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @GetMapping("/{userId}/requests")
  public ResponseEntity<List<FriendRequest>> getFriendRequests(
    @PathVariable UUID userId
  ) {
    List<FriendRequest> friendRequests = friendshipService.getFriendRequests(
      userId
    );
    return ResponseEntity.ok(friendRequests);
  }

  @PostMapping("/{userId}/accept/{requestId}")
  public ResponseEntity<FriendRequest> acceptFriendRequest(
    @PathVariable UUID userId,
    @PathVariable UUID requestId
  ) {
    try {
      FriendRequest friendRequest = friendshipService.acceptFriendRequest(
        userId,
        requestId
      );
      return ResponseEntity.ok(friendRequest);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @PostMapping("/{userId}/deny/{requestId}")
  public ResponseEntity<FriendRequest> denyFriendRequest(
    @PathVariable UUID userId,
    @PathVariable UUID requestId
  ) {
    try {
      FriendRequest friendRequest = friendshipService.denyFriendRequest(
        userId,
        requestId
      );
      return ResponseEntity.ok(friendRequest);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
