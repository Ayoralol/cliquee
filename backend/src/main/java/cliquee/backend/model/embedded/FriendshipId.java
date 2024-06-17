package cliquee.backend.model.embedded;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Embeddable
@Data
public class FriendshipId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long friend1Id;
  private Long friend2Id;

  public FriendshipId() {}

  public FriendshipId(Long user_id, Long friend_id) {
    this.friend1Id = user_id;
    this.friend2Id = friend_id;
  }
}
