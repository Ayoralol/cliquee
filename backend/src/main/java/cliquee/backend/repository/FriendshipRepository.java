package cliquee.backend.repository;

import cliquee.backend.model.Friendship;
import cliquee.backend.model.embedded.FriendshipId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository
  extends JpaRepository<Friendship, FriendshipId> {
  @Query(
    "SELECT f FROM Friendship f WHERE f.user_id = :id OR f.friend_id = :id"
  )
  List<Friendship> findAllFriendshipsByUserId(Long id);
}
