package cliquee.backend.model;

import cliquee.backend.model.embedded.UserGroupId;
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
@Table(name = "user_groups")
public class UserGroup {

  @EmbeddedId
  private UserGroupId id = new UserGroupId();

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("groupId")
  @JoinColumn(name = "group_id")
  private Group group;

  private String role;
  private LocalDateTime joined_at = LocalDateTime.now();

  public void setUser(User user) {
    this.user = user;
    this.id.setUserId(user.getId());
  }

  public void setGroup(Group group) {
    this.group = group;
    this.id.setGroupId(group.getId());
  }

  public UserGroup() {}

  public UserGroup(User user, Group group, String role) {
    this.id = new UserGroupId(user.getId(), group.getId());
    this.user = user;
    this.group = group;
    this.role = role;
  }

  public UserGroup(UserGroupId id, User user, Group group, String role) {
    this.id = id;
    this.setUser(user);
    this.setGroup(group);
    this.role = role;
  }
}
