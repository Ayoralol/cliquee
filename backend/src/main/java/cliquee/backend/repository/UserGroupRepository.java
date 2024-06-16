package cliquee.backend.repository;

import cliquee.backend.model.UserGroup;
import cliquee.backend.model.embedded.UserGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository
  extends JpaRepository<UserGroup, UserGroupId> {}
