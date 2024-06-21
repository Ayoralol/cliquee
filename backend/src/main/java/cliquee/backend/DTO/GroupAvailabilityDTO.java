package cliquee.backend.DTO;

import cliquee.backend.model.GroupAvailability;
import java.time.LocalDateTime;
import java.util.UUID;

public class GroupAvailabilityDTO {

  private UUID id;
  private UUID groupId;
  private UUID userId;
  private LocalDateTime start_time;
  private LocalDateTime end_time;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getGroupId() {
    return groupId;
  }

  public void setGroupId(UUID groupId) {
    this.groupId = groupId;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public LocalDateTime getStart_time() {
    return start_time;
  }

  public void setStart_time(LocalDateTime start_time) {
    this.start_time = start_time;
  }

  public LocalDateTime getEnd_time() {
    return end_time;
  }

  public void setEnd_time(LocalDateTime end_time) {
    this.end_time = end_time;
  }

  public LocalDateTime getCreated_at() {
    return created_at;
  }

  public void setCreated_at(LocalDateTime created_at) {
    this.created_at = created_at;
  }

  public LocalDateTime getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(LocalDateTime updated_at) {
    this.updated_at = updated_at;
  }

  public GroupAvailabilityDTO(GroupAvailability availability) {
    this.id = availability.getId();
    this.groupId = availability.getGroup().getId();
    this.userId = availability.getUser().getId();
    this.start_time = availability.getStart_time();
    this.end_time = availability.getEnd_time();
    this.created_at = availability.getCreated_at();
    this.updated_at = availability.getUpdated_at();
  }
}
