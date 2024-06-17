package cliquee.backend.controller;

import cliquee.backend.model.Notification;
import cliquee.backend.service.NotificationService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @GetMapping("/{id}")
  public ResponseEntity<List<Notification>> getAllNotifications(
    @PathVariable Long id
  ) {
    List<Notification> notifications = notificationService.getAllNotificationsForUser(
      id
    );
    return ResponseEntity.ok(notifications);
  }

  @GetMapping("/{id}/{notification_id}")
  public ResponseEntity<Notification> getNotification(
    @PathVariable Long id,
    @PathVariable Long notificationId
  ) {
    Optional<Notification> notification = notificationService.getNotification(
      id,
      notificationId
    );
    return notification
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/{id}/{notification_id}/respond")
  public ResponseEntity<Notification> respondToNotification(
    @PathVariable Long id,
    @PathVariable Long notificationId,
    @RequestBody Long relatedId,
    @RequestBody String relatedType,
    @RequestBody String response
  ) {
    try {
      Notification notification = notificationService.respondToNotification(
        id,
        notificationId,
        relatedId,
        relatedType,
        response
      );
      return ResponseEntity.ok(notification);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @PostMapping("/{id}/{notification_id}/read")
  public ResponseEntity<Notification> markNotificationAsRead(
    @PathVariable Long id,
    @PathVariable Long notificationId
  ) {
    try {
      Notification notification = notificationService.markNotificationAsRead(
        id,
        notificationId
      );
      return ResponseEntity.ok(notification);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
