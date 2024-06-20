package cliquee.backend.service;

import cliquee.backend.config.ResourceNotFoundException;
import cliquee.backend.model.Event;
import cliquee.backend.model.EventParticipant;
import cliquee.backend.model.Group;
import cliquee.backend.model.GroupAvailability;
import cliquee.backend.model.User;
import cliquee.backend.model.UserGroup;
import cliquee.backend.repository.EventParticipantRepository;
import cliquee.backend.repository.EventRepository;
import cliquee.backend.repository.GroupAvailabilityRepository;
import cliquee.backend.repository.GroupRepository;
import cliquee.backend.repository.UserGroupRepository;
import cliquee.backend.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private UserGroupRepository userGroupRepository;

  @Autowired
  private GroupAvailabilityRepository groupAvailabilityRepository;

  @Autowired
  private EventParticipantRepository groupParticipantsRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Group> getGroupsByUserId(UUID userId) {
    List<UserGroup> userGroups = userGroupRepository.findByUserId(userId);
    return userGroups
      .stream()
      .map(UserGroup::getGroup)
      .collect(Collectors.toList());
  }

  public Group createGroup(UUID userId, Group group) {
    User user = userRepository
      .findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    UserGroup userGroup = new UserGroup();
    userGroup.setUser(user);
    userGroup.setGroup(group);
    userGroup.setRole("ADMIN");
    userGroupRepository.save(userGroup);
    return groupRepository.save(group);
  }

  public List<Group> searchGroups(UUID userId, String keyword) {
    List<UserGroup> userGroups = userGroupRepository.findByUserId(userId);
    List<Group> groups = userGroups
      .stream()
      .map(UserGroup::getGroup)
      .collect(Collectors.toList());
    return groups
      .stream()
      .filter(group ->
        group.getName().toLowerCase().contains(keyword.toLowerCase())
      )
      .collect(Collectors.toList());
  }

  public Group getGroupById(UUID groupId, UUID userId) {
    if (!userGroupRepository.existsByGroupIdAndUserId(groupId, userId)) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    Optional<Group> group = groupRepository.findById(groupId);
    if (group.isPresent()) {
      return group.get();
    } else {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
  }

  public Group updateGroup(UUID groupId, UUID adminId, Group groupDetails) {
    if (
      !userGroupRepository.existsByGroupIdAndUserIdAndRole(
        groupId,
        adminId,
        "ADMIN"
      )
    ) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    Group group = getGroupById(groupId, adminId);
    group.setName(groupDetails.getName());
    group.setDescription(groupDetails.getDescription());
    return groupRepository.save(group);
  }

  public List<Event> getGroupEvents(UUID groupId, UUID userId) {
    if (!userGroupRepository.existsByGroupIdAndUserId(groupId, userId)) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    return eventRepository.findByGroupId(groupId);
  }

  public Event createGroupEvent(UUID groupId, UUID userId, Event event) {
    Group group = getGroupById(groupId, userId);
    event.setGroup(group);
    return eventRepository.save(event);
  }

  public Event getGroupEventById(UUID groupId, UUID eventId, UUID userId) {
    if (!userGroupRepository.existsByGroupIdAndUserId(groupId, userId)) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    return eventRepository
      .findByGroupIdAndId(groupId, eventId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Event not found with id " + eventId + " for group " + groupId
        )
      );
  }

  public Event updateGroupEvent(
    UUID groupId,
    UUID eventId,
    UUID userId,
    Event eventDetails
  ) {
    Event event = getGroupEventById(groupId, eventId, userId);
    event.setName(eventDetails.getName());
    event.setDescription(eventDetails.getDescription());
    event.setStart_time(eventDetails.getStart_time());
    event.setEnd_time(eventDetails.getEnd_time());
    return eventRepository.save(event);
  }

  public void cancelGroupEvent(UUID groupId, UUID eventId, UUID userId) {
    Event event = getGroupEventById(groupId, eventId, userId);
    eventRepository.delete(event);
  }

  public List<GroupAvailability> getGroupAvailabilities(
    UUID groupId,
    UUID userId
  ) {
    if (!userGroupRepository.existsByGroupIdAndUserId(groupId, userId)) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    return groupAvailabilityRepository.findByGroupId(groupId);
  }

  public List<EventParticipant> getGroupEventParticipants(
    UUID groupId,
    UUID eventId,
    UUID userId
  ) {
    if (!userGroupRepository.existsByGroupIdAndUserId(groupId, userId)) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    return groupParticipantsRepository.findByEventId(eventId);
  }

  public List<UserGroup> getGroupMembers(UUID groupId, UUID userId) {
    if (!userGroupRepository.existsByGroupIdAndUserId(groupId, userId)) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    return userGroupRepository.findByGroupId(groupId);
  }

  public void addGroupMember(UUID groupId, UUID friendId, UUID adminId) {
    if (
      !userGroupRepository.existsByGroupIdAndUserIdAndRole(
        groupId,
        adminId,
        "ADMIN"
      )
    ) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    Group group = groupRepository
      .findById(groupId)
      .orElseThrow(() ->
        new ResourceNotFoundException("Group not found with id " + groupId)
      );

    User user = userRepository
      .findById(friendId)
      .orElseThrow(() ->
        new ResourceNotFoundException("User not found with id " + friendId)
      );

    UserGroup userGroup = new UserGroup();
    userGroup.setGroup(group);
    userGroup.setUser(user);
    userGroup.setRole("MEMBER");
    userGroupRepository.save(userGroup);
  }

  public void removeGroupMember(UUID groupId, UUID userId, UUID adminId) {
    if (
      !userGroupRepository.existsByGroupIdAndUserIdAndRole(
        groupId,
        adminId,
        "ADMIN"
      )
    ) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    UserGroup userGroup = userGroupRepository
      .findByGroupIdAndUserId(groupId, userId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "User not found in group with id " + userId + " for group " + groupId
        )
      );
    userGroupRepository.delete(userGroup);
  }

  public UserGroup promoteGroupMember(UUID groupId, UUID userId, UUID adminId) {
    if (
      !userGroupRepository.existsByGroupIdAndUserIdAndRole(
        groupId,
        adminId,
        "ADMIN"
      )
    ) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    UserGroup userGroup = userGroupRepository
      .findByGroupIdAndUserId(groupId, userId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "User not found in group with id " + userId + " for group " + groupId
        )
      );
    userGroup.setRole("ADMIN");
    return userGroupRepository.save(userGroup);
  }

  public UserGroup demoteGroupMember(UUID groupId, UUID userId, UUID adminId) {
    if (
      !userGroupRepository.existsByGroupIdAndUserIdAndRole(
        groupId,
        adminId,
        "ADMIN"
      )
    ) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    UserGroup userGroup = userGroupRepository
      .findByGroupIdAndUserId(groupId, userId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "User not found in group with id " + userId + " for group " + groupId
        )
      );
    userGroup.setRole("MEMBER");
    return userGroupRepository.save(userGroup);
  }

  public GroupAvailability createGroupAvailability(
    UUID groupId,
    UUID userId,
    GroupAvailability availability
  ) {
    if (!userGroupRepository.existsByGroupIdAndUserId(groupId, userId)) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    Group group = groupRepository
      .findById(groupId)
      .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    availability.setGroup(group);
    return groupAvailabilityRepository.save(availability);
  }
}
