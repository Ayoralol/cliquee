package cliquee.backend.repository;

import cliquee.backend.model.EventComment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCommentRepository
  extends JpaRepository<EventComment, UUID> {}
