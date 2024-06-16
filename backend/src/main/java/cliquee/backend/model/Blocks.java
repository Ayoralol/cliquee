package cliquee.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "blocks")
public class Blocks {

  private Long blocker_id;
  private Long blocked_id;
  private LocalDateTime created_at = LocalDateTime.now();
}
