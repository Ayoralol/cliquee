package cliquee.backend.repository;

import cliquee.backend.model.Event;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
  List<Event> findByGroupId(Long groupId);
  Optional<Event> findByGroupIdAndId(Long groupId, Long eventId);
}
