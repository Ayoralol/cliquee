package cliquee.backend.service;

import cliquee.backend.model.Block;
import cliquee.backend.model.embedded.BlockId;
import cliquee.backend.repository.BlockRepository;
import cliquee.backend.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

  @Autowired
  private BlockRepository blockRepository;

  @Autowired
  private UserRepository userRepository;

  public Block blockUser(UUID blockedId, UUID userId) {
    if (
      userRepository.existsById(userId) && userRepository.existsById(blockedId)
    ) {
      Block block = new Block();
      block.setId(new BlockId(userId, blockedId));
      return blockRepository.save(block);
    } else {
      throw new IllegalArgumentException("User or blocked user does not exist");
    }
  }

  public void unblockUser(UUID blockedId, UUID userId) {
    BlockId blockId = new BlockId(userId, blockedId);
    if (blockRepository.existsById(blockId)) {
      blockRepository.deleteById(blockId);
    } else {
      throw new IllegalArgumentException("Block relationship not found");
    }
  }

  public List<Block> getBlockedUsers(UUID userId) {
    return blockRepository.findByBlockerId(userId);
  }
}
