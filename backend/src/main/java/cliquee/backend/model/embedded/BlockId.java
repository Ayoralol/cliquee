package cliquee.backend.model.embedded;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Embeddable
@Data
public class BlockId implements Serializable {

  private static final long serialVersionUID = 1L;

  private UUID blocker_id;
  private UUID blocked_id;

  public BlockId() {}

  public BlockId(UUID blockerId, UUID blockedId) {
    this.blocker_id = blockerId;
    this.blocked_id = blockedId;
  }
}
