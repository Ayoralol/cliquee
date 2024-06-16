package cliquee.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "friendships")
public class Friendships {

  private Long user_id;
  private Long friend_id;
  private String status;
  private LocalDateTime created_at = LocalDateTime.now();
}
