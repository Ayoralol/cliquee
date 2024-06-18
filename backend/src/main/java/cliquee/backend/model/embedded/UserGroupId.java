package cliquee.backend.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.Data;

@Embeddable
@Data
public class UserGroupId implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "group_id")
  private UUID groupId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserGroupId that = (UserGroupId) o;
    return (
      Objects.equals(groupId, that.groupId) &&
      Objects.equals(userId, that.userId)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, userId);
  }

  public UserGroupId() {}

  public UserGroupId(UUID userId, UUID groupId) {
    this.userId = userId;
    this.groupId = groupId;
  }
}
