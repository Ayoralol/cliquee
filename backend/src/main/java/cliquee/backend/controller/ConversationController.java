package cliquee.backend.controller;

import cliquee.backend.model.Conversation;
import cliquee.backend.model.GroupMessage;
import cliquee.backend.model.Message;
import cliquee.backend.service.ConversationService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

  @GetMapping("/{currentUserId}/all")
  public ResponseEntity<List<Conversation>> getAllConversations(
    @PathVariable UUID currentUserId
  ) {
    List<Conversation> conversation = conversationService.getAllConversations(
      currentUserId
    );
    return ResponseEntity.ok(conversation);
  }

  @GetMapping("/{conversationId}")
  public ResponseEntity<Conversation> getConversation(
    @PathVariable UUID conversationId,
    @PathVariable UUID currentUserId
  ) {
    Optional<Conversation> conversation = conversationService.getConversation(
      conversationId,
      currentUserId
    );
    return conversation
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/create/{friendId}")
  public ResponseEntity<Conversation> createConversation(
    @PathVariable UUID friendId,
    @RequestParam UUID currentUserId
  ) {
    try {
      Conversation conversation = conversationService.createConversation(
        friendId,
        currentUserId
      );
      return ResponseEntity.ok(conversation);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @GetMapping("/{conversationId}/messages")
  public ResponseEntity<List<Message>> getMessages(
    @PathVariable UUID conversationId,
    @RequestParam UUID currentUserId
  ) {
    List<Message> message = conversationService.getMessages(
      conversationId,
      currentUserId
    );
    return ResponseEntity.ok(message);
  }

  @PostMapping("/{conversationId}/send")
  public ResponseEntity<Message> sendMessage(
    @PathVariable UUID conversationId,
    @RequestParam UUID currentUserId,
    @RequestBody String messageText
  ) {
    Message message = conversationService.sendMessage(
      conversationId,
      currentUserId,
      messageText
    );
    return ResponseEntity.ok(message);
  }

  @GetMapping("/group/{groupId}")
  public List<GroupMessage> getGroupMessages(
    @PathVariable UUID groupId,
    @RequestParam UUID currentUserId
  ) {
    return conversationService.getGroupMessages(groupId, currentUserId);
  }

  @PostMapping("/group/{groupId}/send")
  public GroupMessage sendGroupMessage(
    @PathVariable UUID groupId,
    @RequestParam UUID currentUserId,
    @RequestParam String messageContent
  ) {
    return conversationService.sendGroupMessage(
      groupId,
      currentUserId,
      messageContent
    );
  }
}
