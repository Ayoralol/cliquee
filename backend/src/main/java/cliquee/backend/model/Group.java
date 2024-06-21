package cliquee.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "`groups`")
public class Group {

  @Id
  @UuidGenerator
  @Column(updatable = false, nullable = false)
  private UUID id;

  private String name;
  private String description;
  private LocalDateTime created_at = LocalDateTime.now();
  private LocalDateTime updated_at = LocalDateTime.now();

  @OneToMany(mappedBy = "group")
  private Set<UserGroup> userGroups = new HashSet<>();

  @OneToMany(mappedBy = "group")
  @JsonIgnore
  private List<GroupAvailability> availabilities;

  @OneToMany(mappedBy = "group")
  private List<Event> events;

  @OneToMany(mappedBy = "group")
  private List<GroupMessage> groupMessages;

  public Group() {}

  public Group(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
