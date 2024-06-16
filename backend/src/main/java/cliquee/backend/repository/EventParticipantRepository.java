package cliquee.backend.repository;

import cliquee.backend.model.EventParticipant;
import cliquee.backend.model.embedded.EventParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipantRepository
  extends JpaRepository<EventParticipant, EventParticipantId> {}
