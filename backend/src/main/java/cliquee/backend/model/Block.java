package cliquee.backend.model;

import cliquee.backend.model.embedded.BlockId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "blocks")
public class Block {

  @EmbeddedId
  private BlockId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("blocker_id")
  @JoinColumn(name = "blocker_id")
  private User blocker;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("blocked_id")
  @JoinColumn(name = "blocked_id")
  private User blocked;

  private LocalDateTime created_at = LocalDateTime.now();
}
