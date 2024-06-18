package cliquee.backend.service;

import cliquee.backend.config.ResourceNotFoundException;
import cliquee.backend.model.Event;
import cliquee.backend.model.EventParticipant;
import cliquee.backend.model.Group;
import cliquee.backend.model.GroupAvailability;
import cliquee.backend.model.UserGroup;
import cliquee.backend.repository.EventParticipantRepository;
import cliquee.backend.repository.EventRepository;
import cliquee.backend.repository.GroupAvailabilityRepository;
import cliquee.backend.repository.GroupRepository;
import cliquee.backend.repository.UserGroupRepository;
import java.util.List;
import java.util.Optional;
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

  public List<Group> getAllGroups() {
    return groupRepository.findAll();
  }

  public Group createGroup(Group group) {
    return groupRepository.save(group);
  }

  public List<Group> searchGroups(String keyword) {
    return groupRepository.findByNameContainingIgnoreCase(keyword);
  }

  public Group getGroupById(Long id) {
    Optional<Group> group = groupRepository.findById(id);
    if (group.isPresent()) {
      return group.get();
    } else {
      throw new ResourceNotFoundException("Group not found with id " + id);
    }
  }

  public Group updateGroup(Long id, Group groupDetails) {
    Group group = getGroupById(id);
    group.setName(groupDetails.getName());
    group.setDescription(groupDetails.getDescription());
    return groupRepository.save(group);
  }

  public List<Event> getGroupEvents(Long id) {
    return eventRepository.findByGroupId(id);
  }

  public Event createGroupEvent(Long groupId, Event event) {
    Group group = getGroupById(groupId);
    event.setGroup(group);
    return eventRepository.save(event);
  }

  public Event getGroupEventById(Long groupId, Long eventId) {
    return eventRepository
      .findByGroupIdAndId(groupId, eventId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Event not found with id " + eventId + " for group " + groupId
        )
      );
  }

  public Event updateGroupEvent(
    Long groupId,
    Long eventId,
    Event eventDetails
  ) {
    Event event = getGroupEventById(groupId, eventId);
    event.setName(eventDetails.getName());
    event.setDescription(eventDetails.getDescription());
    event.setStart_time(eventDetails.getStart_time());
    event.setEnd_time(eventDetails.getEnd_time());
    return eventRepository.save(event);
  }

  public void cancelGroupEvent(Long groupId, Long eventId) {
    Event event = getGroupEventById(groupId, eventId);
    eventRepository.delete(event);
  }

  public List<GroupAvailability> getGroupAvailabilities(Long id) {
    if (!groupRepository.existsById(id)) {
      throw new ResourceNotFoundException("Group not found with id " + id);
    }
    return groupAvailabilityRepository.findByGroupId(id);
  }

  public List<EventParticipant> getGroupEventParticipants(
    Long id,
    Long eventId
  ) {
    if (!groupRepository.existsById(id)) {
      throw new ResourceNotFoundException("Group not found with id " + id);
    }
    return groupParticipantsRepository.findByEventId(eventId);
  }

  public List<UserGroup> getGroupMembers(Long groupId) {
    if (!groupRepository.existsById(groupId)) {
      throw new ResourceNotFoundException("Group not found with id " + groupId);
    }
    return userGroupRepository.findByGroupId(groupId);
  }

  public UserGroup addGroupMember(Long groupId, Long userId) {
    UserGroup userGroup = new UserGroup();
    userGroup.setGroupId(groupId);
    userGroup.setUserId(userId);
    userGroup.setRole("MEMBER");
    return userGroupRepository.save(userGroup);
  }

  public void removeGroupMember(Long groupId, Long userId) {
    UserGroup userGroup = userGroupRepository
      .findByGroupIdAndUserId(groupId, userId)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "User not found in group with id " + userId + " for group " + groupId
        )
      );
    userGroupRepository.delete(userGroup);
  }

  public UserGroup promoteGroupMember(Long groupId, Long userId) {
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

  public UserGroup demoteGroupMember(Long groupId, Long userId) {
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
}
