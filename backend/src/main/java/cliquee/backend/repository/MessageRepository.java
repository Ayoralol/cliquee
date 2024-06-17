package cliquee.backend.repository;

import cliquee.backend.model.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  List<Message> findAllByConversation_Id(Long conversationId);
}
