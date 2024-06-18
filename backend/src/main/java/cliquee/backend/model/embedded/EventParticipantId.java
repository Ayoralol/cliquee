package cliquee.backend.model.embedded;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Embeddable
@Data
public class EventParticipantId implements Serializable {

  private static final long serialVersionUID = 1L;

  private UUID event_id;
  private UUID user_id;
}
