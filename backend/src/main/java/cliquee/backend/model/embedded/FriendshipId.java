package cliquee.backend.model.embedded;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Embeddable
@Data
public class FriendshipId implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long user_id;
  private Long friend_id;

  public FriendshipId() {}

  public FriendshipId(Long user_id, Long friend_id) {
    this.user_id = user_id;
    this.friend_id = friend_id;
  }
}
