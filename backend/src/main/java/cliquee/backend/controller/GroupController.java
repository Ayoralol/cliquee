package cliquee.backend.controller;

import cliquee.backend.model.Event;
import cliquee.backend.model.EventParticipant;
import cliquee.backend.model.Group;
import cliquee.backend.model.GroupAvailability;
import cliquee.backend.model.UserGroup;
import cliquee.backend.service.GroupService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class GroupController {

  @Autowired
  private GroupService groupService;

  @PostMapping("/create")
  public Group createGroup(
    @RequestParam UUID currentUserId,
    @RequestBody Group group
  ) {
    return groupService.createGroup(currentUserId, group);
  }

  @GetMapping("/search")
  public List<Group> searchGroups(
    @RequestParam UUID currentUserId,
    @RequestParam String keyword
  ) {
    return groupService.searchGroups(currentUserId, keyword);
  }

  @GetMapping("/{currentUserId}/all")
  public ResponseEntity<List<Group>> getAllGroupsByUserId(
    @PathVariable UUID currentUserId
  ) {
    List<Group> groups = groupService.getGroupsByUserId(currentUserId);
    return ResponseEntity.ok(groups);
  }

  @GetMapping("/{groupId}")
  public Group getGroupById(
    @PathVariable UUID groupId,
    @RequestParam UUID currentUserId
  ) {
    return groupService.getGroupById(groupId, currentUserId);
  }

  @GetMapping("/{groupId}/availabilities")
  public List<GroupAvailability> getGroupAvailabilities(
    @PathVariable UUID groupId,
    @RequestParam UUID currentUserId
  ) {
    return groupService.getGroupAvailabilities(groupId, currentUserId);
  }

  @PostMapping("/{groupId}/availabilities/create")
  public ResponseEntity<GroupAvailability> createGroupAvailability(
    @PathVariable UUID groupId,
    @RequestParam UUID currentUserId,
    @RequestBody GroupAvailability availability
  ) {
    GroupAvailability newAvailability = groupService.createGroupAvailability(
      groupId,
      currentUserId,
      availability
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(newAvailability);
  }

  @GetMapping("/{groupId}/events")
  public List<Event> getGroupEvents(
    @PathVariable UUID groupId,
    @RequestParam UUID currentUserId
  ) {
    return groupService.getGroupEvents(groupId, currentUserId);
  }

  @PostMapping("/{groupId}/events/create")
  public Event createGroupEvent(
    @PathVariable UUID groupId,
    @RequestParam UUID currentUserId,
    @RequestBody Event event
  ) {
    return groupService.createGroupEvent(groupId, currentUserId, event);
  }

  @GetMapping("/{groupId}/events/{eventId}")
  public Event getGroupEventById(
    @PathVariable UUID groupId,
    @PathVariable UUID eventId,
    @RequestParam UUID currentUserId
  ) {
    return groupService.getGroupEventById(groupId, eventId, currentUserId);
  }

  @PutMapping("/{groupId}/events/{eventId}/update")
  public Event updateGroupEvent(
    @PathVariable UUID groupId,
    @PathVariable UUID eventId,
    @RequestParam UUID currentUserId,
    @RequestBody Event eventDetails
  ) {
    return groupService.updateGroupEvent(
      groupId,
      eventId,
      currentUserId,
      eventDetails
    );
  }

  @DeleteMapping("/{groupId}/events/{eventId}/cancel")
  public ResponseEntity<?> cancelGroupEvent(
    @PathVariable UUID groupId,
    @PathVariable UUID eventId,
    @RequestParam UUID currentUserId
  ) {
    groupService.cancelGroupEvent(groupId, eventId, currentUserId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{groupId}/events/{eventId}/participants")
  public List<EventParticipant> getGroupEventParticipants(
    @PathVariable UUID groupId,
    @PathVariable UUID eventId,
    @RequestParam UUID currentUserId
  ) {
    return groupService.getGroupEventParticipants(
      groupId,
      eventId,
      currentUserId
    );
  }

  @GetMapping("/{groupId}/members")
  public List<UserGroup> getGroupMembers(
    @PathVariable UUID groupId,
    @RequestParam UUID currentUserId
  ) {
    return groupService.getGroupMembers(groupId, currentUserId);
  }

  // GROUP ADMIN ENDPOINTS ONLY BELOW

  @PutMapping("/{groupId}/update")
  public Group updateGroup(
    @PathVariable UUID groupId,
    @RequestParam UUID currentUserId,
    @RequestBody Group groupDetails
  ) {
    return groupService.updateGroup(groupId, currentUserId, groupDetails);
  }

  @PutMapping("/{groupId}/members/add/{friendId}")
  public void addgroupMember(
    @PathVariable UUID groupId,
    @PathVariable UUID friendId,
    @RequestParam UUID currentUserId
  ) {
    groupService.addGroupMember(groupId, friendId, currentUserId);
  }

  @DeleteMapping("/{groupId}/members/remove/{memberId}")
  public ResponseEntity<?> removeGroupMember(
    @PathVariable UUID groupId,
    @PathVariable UUID memberId,
    @RequestParam UUID currentUserId
  ) {
    groupService.removeGroupMember(groupId, memberId, currentUserId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{groupId}/members/promote/{memberId}")
  public UserGroup promoteGroupMember(
    @PathVariable UUID groupId,
    @PathVariable UUID memberId,
    @RequestParam UUID currentUserId
  ) {
    return groupService.promoteGroupMember(groupId, memberId, currentUserId);
  }

  @PostMapping("/{groupId}/members/demote/{memberId}")
  public UserGroup demoteGroupMember(
    @PathVariable UUID groupId,
    @PathVariable UUID memberId,
    @RequestParam UUID currentUserId
  ) {
    return groupService.demoteGroupMember(groupId, memberId, currentUserId);
  }
}
