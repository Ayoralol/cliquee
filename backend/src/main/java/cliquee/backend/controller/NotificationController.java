package cliquee.backend.controller;

import cliquee.backend.model.Notification;
import cliquee.backend.service.NotificationService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @GetMapping("/{currentUserId}")
  public ResponseEntity<List<Notification>> getAllNotifications(
    @PathVariable UUID currentUserId
  ) {
    List<Notification> notifications = notificationService.getAllNotificationsForUser(
      currentUserId
    );
    return ResponseEntity.ok(notifications);
  }

  @GetMapping("/get/{notificationId}")
  public ResponseEntity<Notification> getNotification(
    @PathVariable UUID notificationId,
    @RequestParam UUID currentUserId
  ) {
    Optional<Notification> notification = notificationService.getNotification(
      notificationId,
      currentUserId
    );
    return notification
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/read/{notificationId}")
  public ResponseEntity<Notification> markNotificationAsRead(
    @PathVariable UUID notificationId,
    @RequestParam UUID currentUserId
  ) {
    try {
      Notification notification = notificationService.markNotificationAsRead(
        notificationId,
        currentUserId
      );
      return ResponseEntity.ok(notification);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @PostMapping("/respond/{notificationId}")
  public ResponseEntity<Notification> respondToNotification(
    @PathVariable UUID notificationId,
    @PathVariable UUID currentUserId,
    @RequestBody String type,
    @RequestBody UUID relatedId,
    @RequestBody String response
  ) {
    try {
      Notification notification = notificationService.respondToNotification(
        notificationId,
        currentUserId,
        type,
        relatedId,
        response
      );
      return ResponseEntity.ok(notification);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
