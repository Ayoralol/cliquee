package cliquee.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "event_participants")
public class EventParticipants {

  private Long event_id;
  private Long user_id;
  private String status;
  private LocalDateTime response_time = LocalDateTime.now();
}
