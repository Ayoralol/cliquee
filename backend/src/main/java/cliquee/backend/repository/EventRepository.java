package cliquee.backend.repository;

import cliquee.backend.model.Event;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
  List<Event> findByGroupId(UUID groupId);
  Optional<Event> findByGroupIdAndId(UUID groupId, UUID eventId);
}
