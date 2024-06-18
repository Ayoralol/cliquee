package cliquee.backend.service;

import cliquee.backend.model.Conversation;
import cliquee.backend.model.GroupMessage;
import cliquee.backend.model.Message;
import cliquee.backend.model.User;
import cliquee.backend.repository.ConversationRepository;
import cliquee.backend.repository.GroupMessageRepository;
import cliquee.backend.repository.MessageRepository;
import cliquee.backend.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

  @Autowired
  private ConversationRepository conversationRepository;

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private GroupMessageRepository groupMessageRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Conversation> getAllConversations(UUID userId) {
    return conversationRepository.findAllByUserId(userId);
  }

  public Optional<Conversation> getConversation(
    UUID conversationId,
    UUID userId
  ) {
    return conversationRepository.findByIdAndUserId(conversationId, userId);
  }

  public List<Message> getMessages(UUID conversationId) {
    return messageRepository.findAllByConversation_Id(conversationId);
  }

  public List<GroupMessage> getGroupMessages(UUID groupId) {
    return groupMessageRepository.findAllByGroup_Id(groupId);
  }

  public Message sendMessage(
    UUID conversationId,
    UUID senderId,
    String messageText
  ) {
    Message message = new Message();
    message.setConversationId(conversationId);
    message.setSenderId(senderId);
    message.setMessage(messageText);
    return messageRepository.save(message);
  }

  public GroupMessage sendGroupMessage(
    UUID groupId,
    UUID senderId,
    String messageText
  ) {
    GroupMessage groupMessage = new GroupMessage();
    groupMessage.setGroupId(groupId);
    groupMessage.setUserId(senderId);
    groupMessage.setMessage(messageText);
    return groupMessageRepository.save(groupMessage);
  }

  public Conversation createConversation(UUID user1Id, UUID user2Id) {
    Optional<User> user1 = userRepository.findById(user1Id);
    Optional<User> user2 = userRepository.findById(user2Id);

    if (user1.isPresent() && user2.isPresent()) {
      Conversation conversation = new Conversation();
      conversation.setUser1(user1.get());
      conversation.setUser2(user2.get());
      return conversationRepository.save(conversation);
    } else {
      throw new IllegalArgumentException("User not found");
    }
  }
}
