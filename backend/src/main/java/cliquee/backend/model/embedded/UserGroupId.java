package cliquee.backend.model.embedded;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Embeddable
@Data
public class UserGroupId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long user_id;
  private Long group_id;
}
