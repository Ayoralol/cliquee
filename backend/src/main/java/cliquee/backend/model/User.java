package cliquee.backend.model;

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
@Table(name = "users")
public class User {

  @Id
  @UuidGenerator
  @Column(updatable = false, nullable = false)
  private UUID id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  private String password;
  private String first_name;
  private String last_name;
  private String role = "USER";
  private String privacy = "PUBLIC";
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt = LocalDateTime.now();

  @OneToMany(mappedBy = "blocker")
  private List<Block> blockedUsers;

  @OneToMany(mappedBy = "blocked")
  private List<Block> blockingUsers;

  @OneToMany(mappedBy = "user")
  private Set<UserGroup> userGroups = new HashSet<>();

  @OneToMany(mappedBy = "user")
  private List<GroupAvailability> groupAvailabilities;

  @OneToMany(mappedBy = "sender")
  private List<FriendRequest> sentFriendRequests;

  @OneToMany(mappedBy = "receiver")
  private List<FriendRequest> receivedFriendRequests;

  @OneToMany(mappedBy = "user")
  private List<Notification> notifications;

  @OneToMany(mappedBy = "user")
  private List<AuditLog> auditLogs;

  @OneToMany(mappedBy = "user1")
  private List<Conversation> user1Conversations;

  @OneToMany(mappedBy = "user2")
  private List<Conversation> user2Conversations;

  @OneToMany(mappedBy = "sender")
  private List<Message> messages;

  @OneToMany(mappedBy = "user")
  private List<GroupMessage> groupMessages;

  @OneToMany(mappedBy = "user")
  private List<EventParticipant> eventParticipants;

  @OneToMany(mappedBy = "user")
  private List<EventComment> eventComments;

  @OneToMany(mappedBy = "friend1")
  private List<Friendship> friend1;

  @OneToMany(mappedBy = "friend2")
  private List<Friendship> friend2;

  public User() {}

  public User(
    String username,
    String email,
    String password,
    String firstName,
    String lastName
  ) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.first_name = firstName;
    this.last_name = lastName;
  }

  public User(
    String username,
    String email,
    String password,
    String first_name,
    String last_name,
    String role
  ) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.first_name = first_name;
    this.last_name = last_name;
    this.role = role;
  }
}
