package cliquee.backend.model;

import cliquee.backend.model.embedded.EventParticipantId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "event_participants")
public class EventParticipant {

  @EmbeddedId
  private EventParticipantId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("event_id")
  @JoinColumn(name = "event_id")
  private Event event;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("user_id")
  @JoinColumn(name = "user_id")
  private User user;

  private String status;
  private LocalDateTime response_time = LocalDateTime.now();
}
