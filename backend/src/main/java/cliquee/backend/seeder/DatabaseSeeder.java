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
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements ApplicationRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private UserGroupRepository userGroupRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    seedUsersTable();
    seedGroupsTable();
    seedUserGroupsTable();
  }

  private void seedUsersTable() {
    if (userRepository.count() == 0) {
      List<User> users = List.of(
        new User(
          "ADMIN",
          "admin@hotmail.com",
          "password1234",
          "FirstName",
          "LastName",
          "ADMIN"
        ),
        new User(
          "Bro De Rick",
          "b_rick@gmail.com",
          "password1234",
          "Broderick",
          "Plant"
        ),
        new User(
          "Jentails",
          "jennytails@gmail.com",
          "password1234",
          "Jenny",
          "Tails"
        ),
        new User(
          "Mister",
          "misterblister@outlook.com",
          "password1234",
          "Mister",
          "Blister"
        )
      );
      userRepository.saveAll(users);
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
          new UserGroupId(user2.getId(), group1.getId()),
          user2,
          group1,
          "ADMIN"
        ),
        new UserGroup(
          new UserGroupId(user3.getId(), group1.getId()),
          user3,
          group1,
          "ADMIN"
        ),
        new UserGroup(
          new UserGroupId(user4.getId(), group1.getId()),
          user4,
          group1,
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
