package cliquee.backend.repository;

import cliquee.backend.model.Conversation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository
  extends JpaRepository<Conversation, UUID> {
  @Query(
    "SELECT c FROM Conversation c WHERE c.user1.id = :userId OR c.user2.id = :userId"
  )
  List<Conversation> findAllByUserId(UUID userId);

  @Query(
    "SELECT c FROM Conversation c WHERE c.id = :conversationId AND (c.user1.id = :userId OR c.user2.id = :userId)"
  )
  Optional<Conversation> findByIdAndUserId(UUID conversationId, UUID userId);
}
