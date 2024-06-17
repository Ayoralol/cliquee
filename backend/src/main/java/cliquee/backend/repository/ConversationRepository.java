package cliquee.backend.repository;

import cliquee.backend.model.Conversation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository
  extends JpaRepository<Conversation, Long> {
  List<Conversation> findAllByUserId(Long userId);

  Optional<Conversation> findByIdAndUserId(Long conversationId, Long userId);
}
