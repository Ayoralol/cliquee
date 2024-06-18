package cliquee.backend.repository;

import cliquee.backend.model.GroupMessage;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMessageRepository
  extends JpaRepository<GroupMessage, UUID> {
  List<GroupMessage> findAllByGroup_Id(UUID groupId);
}
