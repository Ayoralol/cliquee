package cliquee.backend.model;

import cliquee.backend.model.embedded.FriendshipId;
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
@Table(name = "friendships")
public class Friendship {

  @EmbeddedId
  private FriendshipId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("user_id")
  @JoinColumn(name = "user_id")
  private User friend1;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("friend_id")
  @JoinColumn(name = "friend_id")
  private User friend2;

  private String status;
  private LocalDateTime created_at = LocalDateTime.now();

  public User getUser() {
    return friend1;
  }

  public User getFriend() {
    return friend2;
  }

  public void setUser(User user) {
    this.friend1 = user;
  }

  public void setFriend(User friend) {
    this.friend2 = friend;
  }
}
