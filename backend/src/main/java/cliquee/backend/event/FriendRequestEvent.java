package cliquee.backend.event;

import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class FriendRequestEvent extends ApplicationEvent {

  private final UUID friendId;
  private final UUID userId;
  private final UUID friendRequestId;

  public FriendRequestEvent(
    Object source,
    UUID friendId,
    UUID userId,
    UUID friendRequestId
  ) {
    super(source);
    this.friendId = friendId;
    this.userId = userId;
    this.friendRequestId = friendRequestId;
  }

  public UUID getFriendId() {
    return friendId;
  }

  public UUID getUserId() {
    return userId;
  }

  public UUID getFriendRequestId() {
    return friendRequestId;
  }
}
