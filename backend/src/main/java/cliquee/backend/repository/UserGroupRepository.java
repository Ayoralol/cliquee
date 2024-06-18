package cliquee.backend.repository;

import cliquee.backend.model.UserGroup;
import cliquee.backend.model.embedded.UserGroupId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository
  extends JpaRepository<UserGroup, UserGroupId> {
  List<UserGroup> findByGroupId(UUID groupId);
  Optional<UserGroup> findByGroupIdAndUserId(UUID groupId, UUID userId);
}
