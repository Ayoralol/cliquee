package cliquee.backend.repository;

import cliquee.backend.model.GroupAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAvailabilityRepository
  extends JpaRepository<GroupAvailability, Long> {}
