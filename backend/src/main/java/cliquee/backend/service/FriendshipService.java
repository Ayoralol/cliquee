package cliquee.backend.service;

import cliquee.backend.event.FriendRequestAcceptEvent;
import cliquee.backend.event.FriendRequestEvent;
import cliquee.backend.model.FriendRequest;
import cliquee.backend.model.Friendship;
import cliquee.backend.model.User;
import cliquee.backend.model.embedded.FriendshipId;
import cliquee.backend.repository.FriendRequestRepository;
import cliquee.backend.repository.FriendshipRepository;
import cliquee.backend.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {

  private final FriendshipRepository friendshipRepository;
  private final FriendRequestRepository friendRequestRepository;
  private final UserRepository userRepository;
  private final ApplicationEventPublisher eventPublisher;

  public FriendshipService(
    FriendshipRepository friendshipRepository,
    FriendRequestRepository friendRequestRepository,
    UserRepository userRepository,
    ApplicationEventPublisher eventPublisher
  ) {
    this.friendshipRepository = friendshipRepository;
    this.friendRequestRepository = friendRequestRepository;
    this.userRepository = userRepository;
    this.eventPublisher = eventPublisher;
  }

  public List<Friendship> getAllFriendships(UUID userId) {
    return friendshipRepository.findAllFriendshipsByUser_Id(userId);
  }

  public List<UUID> getFriendIds(UUID userId) {
    List<Friendship> friendships = friendshipRepository.findAllFriendshipsByUser_Id(
      userId
    );
    return friendships
      .stream()
      .map(friendship -> {
        if (friendship.getUser().getId().equals(userId)) {
          return friendship.getFriend().getId();
        } else {
          return friendship.getUser().getId();
        }
      })
      .collect(Collectors.toList());
  }

  public FriendRequest sendFriendRequest(UUID friendId, UUID userId) {
    Optional<User> user = userRepository.findById(userId);
    Optional<User> friend = userRepository.findById(friendId);

    if (user.isPresent() && friend.isPresent()) {
      FriendRequest friendRequest = new FriendRequest();
      friendRequest.setSender(user.get());
      friendRequest.setReceiver(friend.get());
      friendRequest.setStatus("PENDING");
      friendRequestRepository.save(friendRequest);

      eventPublisher.publishEvent(
        new FriendRequestEvent(this, friendId, userId, friendRequest.getId())
      );
      return friendRequest;
    } else {
      throw new IllegalArgumentException("User or Friend not found");
    }
  }

  public List<FriendRequest> getFriendRequests(UUID userId) {
    return friendRequestRepository.findByReceiverId(userId);
  }

  public FriendRequest acceptFriendRequest(UUID requestId, UUID userId) {
    Optional<FriendRequest> optionalFriendRequest = friendRequestRepository.findById(
      requestId
    );

    if (
      optionalFriendRequest.isPresent() &&
      optionalFriendRequest.get().getReceiver().getId().equals(userId)
    ) {
      FriendRequest friendRequest = optionalFriendRequest.get();
      friendRequest.setStatus("ACCEPTED");
      friendRequestRepository.save(friendRequest);

      Friendship friendship = new Friendship();
      friendship.setId(
        new FriendshipId(
          friendRequest.getSender().getId(),
          friendRequest.getReceiver().getId()
        )
      );
      friendship.setUser(friendRequest.getSender());
      friendship.setFriend(friendRequest.getReceiver());
      friendship.setStatus("FRIENDS");
      friendshipRepository.save(friendship);

      eventPublisher.publishEvent(
        new FriendRequestAcceptEvent(
          this,
          friendRequest.getSender().getId(),
          friendRequest.getReceiver().getId(),
          friendRequest.getId()
        )
      );

      return friendRequest;
    } else {
      throw new IllegalArgumentException(
        "Friend request not found or does not belong to the user"
      );
    }
  }

  public FriendRequest denyFriendRequest(UUID requestId, UUID userId) {
    Optional<FriendRequest> optionalFriendRequest = friendRequestRepository.findById(
      requestId
    );

    if (
      optionalFriendRequest.isPresent() &&
      optionalFriendRequest.get().getReceiver().getId().equals(userId)
    ) {
      FriendRequest friendRequest = optionalFriendRequest.get();
      friendRequest.setStatus("DENIED");
      return friendRequestRepository.save(friendRequest);
    } else {
      throw new IllegalArgumentException(
        "Friend request not found or does not belong to the user"
      );
    }
  }
}
