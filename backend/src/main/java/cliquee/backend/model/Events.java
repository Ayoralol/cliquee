package cliquee.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "events")
public class Events {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long group_id;
  private String name;
  private String description;
  private String location;
  private LocalDateTime start_time;
  private LocalDateTime end_time;
  private LocalDateTime created_at = LocalDateTime.now();
  private LocalDateTime updated_at = LocalDateTime.now();
}
