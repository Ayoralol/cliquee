## Endpoint Address Documentation

### Users Endpoints

#### Lead with /users

- /{userId}
  - GET
  - Params = currentUserId
  - Get a users profile
- /{currentUserId}/friends
  - GET
  - Get all your friends
- /username/{username}
  - GET
  - Params = currentUserId
  - Get a user by username
- /email/{email}
  - GET
  - Params = currentUserId
  - Get a user by email
- /search
  - GET
  - Params = currentUserId, keyword
  - Search for users
- /update/{currentUserId}
  - PUT
  - Body = `User`
  - Update your profile
- /create
  - POST
  - Body = `User`
  - Create a new User
- /delete/{currentUserId}
  - DELETE
  - Delete a user
- /change-password/{currentUserId}
  - PUT
  - Body = {oldPassword, newPassword}
  - change a users password
- /block/{blockedId}
  - POST
  - Params = currentUserId
  - Block a user
- /unblock/{blockedId}
  - DELETE
  - Params = currentUserId
  - Unblock a user

### Groups Endpoints

#### Lead with /groups

- /create
  - POST
  - Body = `Group`
  - Create a new group
- /search
  - GET
  - Params = currentUserId, keyword
  - Search your groups
- /{currentUserId}/all
  - GET
  - Get all of a users groups
- /{groupId}
  - GET
  - Params = currentUserId
  - Get a specific group
- /{groupId}/availabilities
  - GET
  - Params = currentUserId
  - Get a list of group availabilities
- /{groupId}/availabilities/create
  - POST
  - Params = currentUserId
  - Body = `GroupAvailability`
  - Update a groupAvailability
- /{groupId}/events
  - GET
  - Params = currentUserId
  - Get all group events
- /{groupId}/events/create
  - POST
  - Params = currentUserId
  - Body = `Event`
  - Create an event
- /{groupId}/events/{eventId}
  - GET
  - Params = currentUserId
  - Get a specific event
- /{groupId}/events/{eventId}/update
  - PUT
  - Params = currentUserId
  - body = `Event`
  - Update an event
- /{groupId}/events/{eventId}/cancel
  - DELETE
  - Params = currentUserId
  - Delete an event
- /{groupId}/events/{eventId}/attendees
  - GET
  - Params = currentUserId
  - Get attendants for an event
- /{groupId}/members
  - GET
  - Param = currentUserId
  - Get a list of group members

#### Group Admin Only endpoints

- /{groupId}/update
  - PUT
  - Params = currentUserId
  - Body = `Group`
  - Update a group
- /{groupId}/members/add-member/{friendId}
  - POST
  - Params = currentUserId
  - Add a friend to the group
- /{groupId}/member/remove-member/{memberId}
  - DELETE
  - Params = currentUserId
  - remove a group member
- /{groupId}/member/promote/{memberId}
  - POST
  - Params = currentUserId
  - Promote a group member to Admin
- /{groupId}/member/demote/{memberId}
  - POST
  - Params = currentUserId
  - Demote a current Admin

### Friendships Endpoints

#### Lead with /friendships

- /{currentUserId}
  - GET
  - Your friends List
- /request/{friendId}
  - POST
  - Params = currentUserId
  - Request to add a friend
- /requests/{currentUserId}
  - GET
  - Get your friend requests
- /requests/accept/{requestId}
  - POST
  - Params = currentUserId
  - Accept a friend request
- /requests/deny/{requestId}
  - POST
  - Params = currentUserId
  - Deny a friend request

### Notifications Endpoints

#### Lead with /notifications

- /{currentUserId}
  - GET
  - Get all your notifications
- /get/{notificationId}
  - GET
  - Params = currentUserId
  - Get a specific notification
- /read/{notificationId}
  - POST
  - Params = currentUserId
  - Mark the notification as read
- /respond/{notificationId}
  - POST
  - Params = currentUserId
  - Respond to a notification

### Conversations Endpoints

#### lead with /conversations

- /{currentUserId}/all
  - GET
  - Get all you conversations
- /{conversationId}
  - GET
  - Params = currentUserId
  - Get a specific conversation
- /create/{friendId}
  - POST
  - Params = currentUserId
  - Create a conversation with a friend
- /{conversationId}/messages
  - GET
  - Params = currentUserId
  - Get the messages of a conversation
- /{conversationId}/send
  - POST
  - Params = currentUserId
  - Send a message to a conversation
- /group/{groupId}
  - GET
  - Params = currentUserId
  - Access a group conversation
- /group/{groupId}/send
  - POST
  - Params = currentUserId
  - Send a group message

### Admin Endpoints

#### Lead with /admin

- /users
  - GET
  - Params = adminId
  - Get all Users
- /groups
  - GET
  - Params = adminId
  - Get all groups
- /logs
  - GET
  - Params = adminId
  - Get all Activity Logs
- /logs/clear
  - DELETE
  - Params = adminId
  - Clear activity logs
