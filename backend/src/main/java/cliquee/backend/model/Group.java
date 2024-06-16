package cliquee.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "`groups`")
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private LocalDateTime created_at = LocalDateTime.now();
  private LocalDateTime updated_at = LocalDateTime.now();

  @OneToMany(mappedBy = "group")
  private List<UserGroup> userGroups;

  @OneToMany(mappedBy = "group")
  private List<GroupAvailability> availabilities;

  @OneToMany(mappedBy = "group")
  private List<Event> events;

  @OneToMany(mappedBy = "group")
  private List<GroupMessage> groupMessages;
}
