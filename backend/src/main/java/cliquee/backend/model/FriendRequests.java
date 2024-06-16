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
@Table(name = "friend_requests")
public class FriendRequests {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long sender_id;
  private Long receiver_id;
  private String status = "PENDING";
  private LocalDateTime created_at = LocalDateTime.now();
  private LocalDateTime updated_at = LocalDateTime.now();
}
