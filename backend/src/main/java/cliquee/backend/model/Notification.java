package cliquee.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "notifications")
public class Notification {

  @Id
  @UuidGenerator
  @Column(updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private UUID sender_id;
  private String type;
  private UUID related_id;
  private String message;
  private boolean is_read = false;
  private LocalDateTime created_at = LocalDateTime.now();

  public Notification setRead(boolean isRead) {
    this.is_read = isRead;
    return this;
  }
}
