package cliquee.backend.model.embedded;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Embeddable
@Data
public class FriendshipId implements Serializable {

  private static final long serialVersionUID = 1L;

  private UUID friend1Id;
  private UUID friend2Id;

  public FriendshipId() {}

  public FriendshipId(UUID user_id, UUID friend_id) {
    this.friend1Id = user_id;
    this.friend2Id = friend_id;
  }
}
