package cliquee.backend.repository;

import cliquee.backend.model.FriendRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository
  extends JpaRepository<FriendRequest, Long> {
  List<FriendRequest> findByReceiverId(Long receiverId);
}
