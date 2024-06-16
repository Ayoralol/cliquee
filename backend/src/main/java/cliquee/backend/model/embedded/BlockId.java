package cliquee.backend.model.embedded;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Embeddable
@Data
public class BlockId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long blocker_id;
  private Long blocked_id;
}
