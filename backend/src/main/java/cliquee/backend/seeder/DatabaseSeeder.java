package cliquee.backend.seeder;

import cliquee.backend.model.Group;
import cliquee.backend.model.User;
import cliquee.backend.model.UserGroup;
import cliquee.backend.model.embedded.UserGroupId;
import cliquee.backend.repository.GroupRepository;
import cliquee.backend.repository.UserGroupRepository;
import cliquee.backend.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements ApplicationRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private UserGroupRepository userGroupRepository;

  // @Autowired
  // private AuditLogRepository auditLogRepository;

  // @Autowired
  // private BlockRepository blockRepository;

  // @Autowired
  // private ConversationRepository conversationRepository;

  // @Autowired
  // private EventCommentRepository eventCommentRepository;

  // @Autowired
  // private EventParticipantRepository eventParticipantRepository;

  // @Autowired
  // private EventRepository eventRepository;

  // @Autowired
  // private FriendRequestRepository friendRequestRepository;

  // @Autowired
  // private FriendshipRepository friendshipRepository;

  // @Autowired
  // private GroupAvailabilityRepository groupAvailabilityRepository;

  // @Autowired
  // private GroupMessageRepository groupMessageRepository;

  // @Autowired
  // private MessageRepository messageRepository;

  // @Autowired
  // private NotificationRepository notificationRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    seedUsersTable();
    seedGroupsTable();
    seedUserGroupsTable();
    // seedGroupAvailabilitiesTable();
    // seedEventsTable();
    // seedEventParticipantsTable();
    // seedEventCommentsTable();
    // seedConversationsTable();
    // seedMessagesTable();
    // seedGroupMessagesTable();
    // seedFriendshipsTable();
    // seedFriendRequestsTable();
    // seedNotificationsTable();
    // seedBlocksTable();
    // seedAuditLogsTable();
  }

  private void seedUsersTable() {
    if (userRepository.count() == 0) {
      User user1 = new User();
      user1.setUsername("ADMIN");
      user1.setEmail("admin@hotmail.com");
      user1.setPassword(passwordEncoder.encode("password1234"));
      user1.setFirst_name("FirstName");
      user1.setLast_name("LastName");
      user1.setRole("ADMIN,USER");

      User user2 = new User();
      user2.setUsername("Bro De Rick");
      user2.setEmail("b_rick@gmail.com");
      user2.setPassword(passwordEncoder.encode("password1234"));
      user2.setFirst_name("Broderick");
      user2.setLast_name("Plant");

      User user3 = new User();
      user3.setUsername("Jentails");
      user3.setEmail("jennytails@gmail.com");
      user3.setPassword(passwordEncoder.encode("password1234"));
      user3.setFirst_name("Jenny");
      user3.setLast_name("Tails");

      User user4 = new User();
      user4.setUsername("Mister");
      user4.setEmail("misterblister@outlook.com");
      user4.setPassword(passwordEncoder.encode("password1234"));
      user4.setFirst_name("Mister");
      user4.setLast_name("Blister");

      userRepository.saveAll(List.of(user1, user2, user3, user4));
    }
  }

  private void seedGroupsTable() {
    if (groupRepository.count() == 0) {
      List<Group> groups = List.of(
        new Group("Group 1", "This is the first group"),
        new Group("Group 2", "This is the second group"),
        new Group("Group 3", "This is the third group"),
        new Group("Group 4", "This is the fourth group")
      );
      groupRepository.saveAll(groups);
    }
  }

  private void seedUserGroupsTable() {
    if (userGroupRepository.count() == 0) {
      User user1 = userRepository.findByUsername("ADMIN").get();
      User user2 = userRepository.findByUsername("Bro De Rick").get();
      User user3 = userRepository.findByUsername("Jentails").get();
      User user4 = userRepository.findByUsername("Mister").get();
      List<Group> groups = groupRepository.findAll();
      Group group1 = groups
        .stream()
        .filter(group -> "Group 1".equals(group.getName()))
        .findFirst()
        .get();
      Group group2 = groups
        .stream()
        .filter(group -> "Group 2".equals(group.getName()))
        .findFirst()
        .get();
      Group group3 = groups
        .stream()
        .filter(group -> "Group 3".equals(group.getName()))
        .findFirst()
        .get();
      Group group4 = groups
        .stream()
        .filter(group -> "Group 4".equals(group.getName()))
        .findFirst()
        .get();

      List<UserGroup> userGroups = List.of(
        new UserGroup(
          new UserGroupId(user1.getId(), group1.getId()),
          user1,
          group1,
          "ADMIN"
        ),
        new UserGroup(
          new UserGroupId(user2.getId(), group2.getId()),
          user2,
          group2,
          "ADMIN"
        ),
        new UserGroup(
          new UserGroupId(user3.getId(), group3.getId()),
          user3,
          group3,
          "ADMIN"
        ),
        new UserGroup(
          new UserGroupId(user4.getId(), group4.getId()),
          user4,
          group4,
          "ADMIN"
        ),
        new UserGroup(
          new UserGroupId(user1.getId(), group2.getId()),
          user1,
          group2,
          "MEMBER"
        ),
        new UserGroup(
          new UserGroupId(user2.getId(), group3.getId()),
          user2,
          group3,
          "MEMBER"
        ),
        new UserGroup(
          new UserGroupId(user3.getId(), group4.getId()),
          user3,
          group4,
          "MEMBER"
        ),
        new UserGroup(
          new UserGroupId(user4.getId(), group1.getId()),
          user4,
          group1,
          "MEMBER"
        ),
        new UserGroup(
          new UserGroupId(user1.getId(), group4.getId()),
          user1,
          group4,
          "MEMBER"
        )
      );
      userGroupRepository.saveAll(userGroups);
    }
  }
}
