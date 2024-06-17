package cliquee.backend.repository;

import cliquee.backend.model.Block;
import cliquee.backend.model.embedded.BlockId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, BlockId> {
  List<Block> findByBlockerId(Long blockerId);
}
