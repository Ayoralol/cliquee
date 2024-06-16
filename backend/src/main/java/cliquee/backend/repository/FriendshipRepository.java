package cliquee.backend.repository;

import cliquee.backend.model.Friendship;
import cliquee.backend.model.embedded.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository
  extends JpaRepository<Friendship, FriendshipId> {}
