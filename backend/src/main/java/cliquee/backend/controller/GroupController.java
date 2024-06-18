package cliquee.backend.controller;

import cliquee.backend.model.Event;
import cliquee.backend.model.EventParticipant;
import cliquee.backend.model.Group;
import cliquee.backend.model.GroupAvailability;
import cliquee.backend.model.UserGroup;
import cliquee.backend.service.GroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class GroupController {

  @Autowired
  private GroupService groupService;

  @GetMapping("/all")
  public List<Group> getAllGroups() {
    return groupService.getAllGroups();
  }

  @PostMapping("/create")
  public Group createGroup(@RequestBody Group group) {
    return groupService.createGroup(group);
  }

  @GetMapping("/search/{keyword}")
  public List<Group> searchGroups(@PathVariable String keyword) {
    return groupService.searchGroups(keyword);
  }

  @GetMapping("/{id}")
  public Group getGroupById(@PathVariable Long id) {
    return groupService.getGroupById(id);
  }

  @PutMapping("/{id}/update")
  public Group updateGroup(
    @PathVariable Long id,
    @RequestBody Group groupDetails
  ) {
    return groupService.updateGroup(id, groupDetails);
  }

  @GetMapping("/{id}/availabilities")
  public List<GroupAvailability> getGroupAvailabilities(@PathVariable Long id) {
    return groupService.getGroupAvailabilities(id);
  }

  @GetMapping("/{id}/events")
  public List<Event> getGroupEvents(@PathVariable Long id) {
    return groupService.getGroupEvents(id);
  }

  @PostMapping("/{id}/events/create")
  public Event createGroupEvent(
    @PathVariable Long id,
    @RequestBody Event event
  ) {
    return groupService.createGroupEvent(id, event);
  }

  @GetMapping("/{id}/events/{event_id}")
  public Event getGroupEventById(
    @PathVariable Long id,
    @PathVariable Long event_id
  ) {
    return groupService.getGroupEventById(id, event_id);
  }

  @PutMapping("/{id}/events/{event_id}/update")
  public Event updateGroupEvent(
    @PathVariable Long id,
    @PathVariable Long event_id,
    @RequestBody Event eventDetails
  ) {
    return groupService.updateGroupEvent(id, event_id, eventDetails);
  }

  @DeleteMapping("/{id}/events/{event_id}/cancel")
  public ResponseEntity<?> cancelGroupEvent(
    @PathVariable Long id,
    @PathVariable Long event_id
  ) {
    groupService.cancelGroupEvent(id, event_id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}/events/{event_id}/participants")
  public List<EventParticipant> getGroupEventParticipants(
    @PathVariable Long id,
    @PathVariable Long event_id
  ) {
    return groupService.getGroupEventParticipants(id, event_id);
  }

  @GetMapping("/{id}/members")
  public List<UserGroup> getGroupMembers(@PathVariable Long id) {
    return groupService.getGroupMembers(id);
  }

  @PutMapping("/{id}/members/{user_id}/add")
  public UserGroup addgroupMember(
    @PathVariable Long id,
    @PathVariable Long user_id
  ) {
    return groupService.addGroupMember(id, user_id);
  }

  @DeleteMapping("/{id}/members/{user_id}/remove")
  public ResponseEntity<?> removeGroupMember(
    @PathVariable Long id,
    @PathVariable Long user_id
  ) {
    groupService.removeGroupMember(id, user_id);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/members/{user_id}/promote")
  public UserGroup promoteGroupMember(
    @PathVariable Long id,
    @PathVariable Long user_id
  ) {
    return groupService.promoteGroupMember(id, user_id);
  }

  @PostMapping("/{id}/members/{user_id}/demote")
  public UserGroup demoteGroupMember(
    @PathVariable Long id,
    @PathVariable Long user_id
  ) {
    return groupService.demoteGroupMember(id, user_id);
  }
}
