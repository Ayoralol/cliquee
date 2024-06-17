package cliquee.backend.service;

import cliquee.backend.model.FriendRequest;
import cliquee.backend.model.Friendship;
import cliquee.backend.model.User;
import cliquee.backend.model.embedded.FriendshipId;
import cliquee.backend.repository.FriendRequestRepository;
import cliquee.backend.repository.FriendshipRepository;
import cliquee.backend.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {

  @Autowired
  private FriendshipRepository friendshipRepository;

  @Autowired
  private FriendRequestRepository friendRequestRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Friendship> getAllFriendships(Long userId) {
    return friendshipRepository.findAllFriendshipsByUserId(userId);
  }

  public List<Long> getFriendIds(Long userId) {
    List<Friendship> friendships = friendshipRepository.findAllFriendshipsByUserId(
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

  public FriendRequest sendFriendRequest(Long userId, Long friendId) {
    Optional<User> user = userRepository.findById(userId);
    Optional<User> friend = userRepository.findById(friendId);

    if (user.isPresent() && friend.isPresent()) {
      FriendRequest friendRequest = new FriendRequest();
      friendRequest.setSender(user.get());
      friendRequest.setReceiver(friend.get());
      friendRequest.setStatus("PENDING");
      return friendRequestRepository.save(friendRequest);
    } else {
      throw new IllegalArgumentException("User or Friend not found");
    }
  }

  public List<FriendRequest> getFriendRequests(Long userId) {
    return friendRequestRepository.findByReceiverId(userId);
  }

  public FriendRequest acceptFriendRequest(Long userId, Long requestId) {
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

      return friendRequest;
    } else {
      throw new IllegalArgumentException(
        "Friend request not found or does not belong to the user"
      );
    }
  }

  public FriendRequest denyFriendRequest(Long userId, Long requestId) {
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
