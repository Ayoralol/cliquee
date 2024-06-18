package cliquee.backend.repository;

import cliquee.backend.model.FriendRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository
  extends JpaRepository<FriendRequest, UUID> {
  List<FriendRequest> findByReceiverId(UUID receiverId);
}
