package cliquee.backend.DTO;

import cliquee.backend.model.Event;
import java.time.LocalDateTime;
import java.util.UUID;

public class EventDTO {

  private UUID id;
  private UUID groupId;
  private String name;
  private String description;
  private String location;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
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

  public EventDTO(Event event) {
    this.id = event.getId();
    this.groupId = event.getGroup().getId();
    this.name = event.getName();
    this.description = event.getDescription();
    this.location = event.getLocation();
    this.start_time = event.getStart_time();
    this.end_time = event.getEnd_time();
    this.created_at = event.getCreated_at();
    this.updated_at = event.getUpdated_at();
  }
}
