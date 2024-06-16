package cliquee.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "user_groups")
public class UserGroups {

  private Long user_id;
  private Long group_id;
  private String role;
  private LocalDateTime joined_at = LocalDateTime.now();
}
