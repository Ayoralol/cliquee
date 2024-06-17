package cliquee.backend.repository;

import cliquee.backend.model.Notification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository
  extends JpaRepository<Notification, Long> {
  List<Notification> findAllById(Long userId);

  Optional<Notification> findByIdAndUserId(Long notificationId, Long userId);
}
