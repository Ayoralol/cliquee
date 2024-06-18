package cliquee.backend.repository;

import cliquee.backend.model.Group;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
  Group findByName(String name);
  List<Group> findByNameContainingIgnoreCase(String keyword);
}
