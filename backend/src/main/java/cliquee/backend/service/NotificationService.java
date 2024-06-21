package cliquee.backend.service;

import cliquee.backend.event.FriendRequestAcceptEvent;
import cliquee.backend.event.FriendRequestEvent;
import cliquee.backend.model.Notification;
import cliquee.backend.repository.NotificationRepository;
import cliquee.backend.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final FriendshipService friendshipService;
  private final UserService userService;
  private final UserRepository userRepository;

  public NotificationService(
    NotificationRepository notificationRepository,
    FriendshipService friendshipService,
    UserService userService,
    UserRepository userRepository
  ) {
    this.notificationRepository = notificationRepository;
    this.friendshipService = friendshipService;
    this.userService = userService;
    this.userRepository = userRepository;
  }

  public List<Notification> getAllNotificationsForUser(UUID userId) {
    return notificationRepository.findAllById(userId);
  }

  public Optional<Notification> getNotification(
    UUID notificationId,
    UUID userId
  ) {
    Optional<Notification> notification = notificationRepository.findByIdAndUserId(
      notificationId,
      userId
    );
    notification.ifPresent(notif -> {
      notif.setRead(true);
      notificationRepository.save(notif);
    });
    return notification;
  }

  public Notification respondToNotification(
    UUID notificationId,
    UUID userId,
    String type,
    UUID relatedId,
    String response
  ) {
    Optional<Notification> optionalNotification = notificationRepository.findByIdAndUserId(
      notificationId,
      userId
    );
    if (optionalNotification.isPresent()) {
      Notification notification = optionalNotification.get();

      // RESPONSE LOGIC, ADD TO SWITCH CASE WHEN ADDING NEW RELATED TYPES
      switch (type.toUpperCase()) {
        case "FRIEND_REQUEST":
          handleFriendRequestResponse(userId, relatedId, response);
          break;
        default:
          throw new IllegalArgumentException(
            "Invalid related type for notification"
          );
      }

      notification.setRead(true);
      return notificationRepository.save(notification);
    } else {
      throw new IllegalArgumentException(
        "Notification not found or does not belong to the user"
      );
    }
  }

  private void handleFriendRequestResponse(
    UUID userId,
    UUID relatedId,
    String response
  ) {
    switch (response.toUpperCase()) {
      case "ACCEPT":
        friendshipService.acceptFriendRequest(userId, relatedId);
        break;
      case "DENY":
        friendshipService.denyFriendRequest(userId, relatedId);
        break;
      default:
        throw new IllegalArgumentException(
          "Invalid response for friend request"
        );
    }
  }

  public Notification markNotificationAsRead(UUID notificationId, UUID userId) {
    Optional<Notification> optionalNotification = notificationRepository.findByIdAndUserId(
      notificationId,
      userId
    );
    if (optionalNotification.isPresent()) {
      Notification notification = optionalNotification.get();
      notification.setRead(true);
      return notificationRepository.save(notification);
    } else {
      throw new IllegalArgumentException(
        "Notification not found or does not belong to the user"
      );
    }
  }

  @EventListener
  public void handleFriendRequestEvent(FriendRequestEvent event) {
    createNotification(
      event.getFriendId(),
      event.getUserId(),
      "FRIEND_REQUEST",
      event.getFriendRequestId(),
      "You have a new friend request from " +
      userRepository.findUsernameById(event.getUserId())
    );
  }

  @EventListener
  public void handleFriendRequestAcceptEvent(FriendRequestAcceptEvent event) {
    createNotification(
      event.getFriendId(),
      event.getUserId(),
      "FRIEND_REQUEST_ACCEPTED",
      event.getFriendRequestId(),
      userRepository.findUsernameById(event.getUserId()) +
      "Has accepted your friend request!"
    );
  }

  public Notification createNotification(
    UUID receiverId,
    UUID senderId,
    String type,
    UUID relatedId,
    String message
  ) {
    Notification notification = new Notification();
    notification.setUser(userService.getUserById(receiverId, senderId).get());
    notification.setSender_id(senderId);
    notification.setType(type.toUpperCase());
    notification.setRelated_id(relatedId);
    notification.setMessage(message);
    return notificationRepository.save(notification);
  }
}
// Notification List
// GROUPS ****
// Added to a group
// Removed from a group
// Event created in a group
// Event Updated in a group
// Event Cancelled in a group
// Promoted in a group
// Demoted in a group
