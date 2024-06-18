package cliquee.backend.repository;

import cliquee.backend.model.GroupAvailability;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAvailabilityRepository
  extends JpaRepository<GroupAvailability, UUID> {
  List<GroupAvailability> findByGroupId(UUID groupId);
}
