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
  private UserGroupId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("user_id")
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("group_id")
  @JoinColumn(name = "group_id")
  private Group group;

  private String role;
  private LocalDateTime joined_at = LocalDateTime.now();
}
