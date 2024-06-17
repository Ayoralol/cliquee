package cliquee.backend.controller;

import cliquee.backend.model.Conversation;
import cliquee.backend.model.GroupMessage;
import cliquee.backend.model.Message;
import cliquee.backend.service.ConversationService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

  @Autowired
  private ConversationService conversationService;

  @GetMapping("/{id}")
  public ResponseEntity<List<Conversation>> getAllConversations(
    @PathVariable Long id
  ) {
    List<Conversation> conversation = conversationService.getAllConversations(
      id
    );
    return ResponseEntity.ok(conversation);
  }

  @GetMapping("/{id}/{conversation_id}")
  public ResponseEntity<Conversation> getConversation(
    @PathVariable Long id,
    @PathVariable Long conversationId
  ) {
    Optional<Conversation> conversation = conversationService.getConversation(
      id,
      conversationId
    );
    return conversation
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/{id}/{conversation_id}/messages")
  public ResponseEntity<List<Message>> getMessages(
    @PathVariable Long conversationId
  ) {
    List<Message> message = conversationService.getMessages(conversationId);
    return ResponseEntity.ok(message);
  }

  @GetMapping("/{id}/group/{group_id}")
  public ResponseEntity<List<GroupMessage>> getGroupMessages(
    @PathVariable Long id,
    @PathVariable Long groupId
  ) {
    List<GroupMessage> groupMessage = conversationService.getGroupMessages(
      groupId
    );
    return ResponseEntity.ok(groupMessage);
  }

  @PostMapping("/{id}/{conversation_id}/send")
  public ResponseEntity<Message> sendMessage(
    @PathVariable Long id,
    @PathVariable Long conversationId,
    @RequestBody String messageText
  ) {
    Message message = conversationService.sendMessage(
      conversationId,
      id,
      messageText
    );
    return ResponseEntity.ok(message);
  }

  @PostMapping("/{id}/group/{group_id}/send")
  public ResponseEntity<GroupMessage> sendGroupMessage(
    @PathVariable Long id,
    @PathVariable Long group_id,
    @RequestBody String messageText
  ) {
    GroupMessage groupMessage = conversationService.sendGroupMessage(
      group_id,
      id,
      messageText
    );
    return ResponseEntity.ok(groupMessage);
  }

  @PostMapping("/{user1Id}/create")
  public ResponseEntity<Conversation> createConversation(
    @PathVariable Long user1Id,
    @RequestParam Long user2Id
  ) {
    try {
      Conversation conversation = conversationService.createConversation(
        user1Id,
        user2Id
      );
      return ResponseEntity.ok(conversation);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }
}
