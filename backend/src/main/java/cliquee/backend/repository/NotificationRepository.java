package cliquee.backend.repository;

import cliquee.backend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository
  extends JpaRepository<Notification, Long> {}
