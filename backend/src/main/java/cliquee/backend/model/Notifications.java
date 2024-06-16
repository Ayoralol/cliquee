package cliquee.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "notifications")
public class Notifications {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long user_id;
  private String type;
  private String message;
  private Long related_id;
  private String related_type;
  private boolean is_read = false;
  private LocalDateTime created_at = LocalDateTime.now();
}
