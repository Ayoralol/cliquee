package cliquee.backend.repository;

import cliquee.backend.model.Notification;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository
  extends JpaRepository<Notification, UUID> {
  List<Notification> findAllById(UUID userId);

  Optional<Notification> findByIdAndUserId(UUID notificationId, UUID userId);
}
