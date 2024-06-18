package cliquee.backend.repository;

import cliquee.backend.model.GroupAvailability;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAvailabilityRepository
  extends JpaRepository<GroupAvailability, Long> {
  List<GroupAvailability> findByGroupId(Long groupId);
}
