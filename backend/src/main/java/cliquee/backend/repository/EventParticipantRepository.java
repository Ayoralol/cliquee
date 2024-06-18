package cliquee.backend.repository;

import cliquee.backend.model.EventParticipant;
import cliquee.backend.model.embedded.EventParticipantId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipantRepository
  extends JpaRepository<EventParticipant, EventParticipantId> {
  List<EventParticipant> findByEventId(Long eventId);
}
