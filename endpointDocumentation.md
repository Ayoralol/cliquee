## Endpoint Address Documentation

### Users Endpoints

#### Lead with /users

- /{user_id}
  - GET
  - Params = current_user_id
  - Get a users profile
- /username/{username}
  - GET
  - Params = current_user_id
  - Get a user by username
- /email/{email}
  - GET
  - Params = current_user_id
  - Get a user by email
- /search
  - GET
  - Params = current_user_id, keyword
  - Search for users
- /update/{current_user_id}
  - PUT
  - Body = `User`
  - Update your profile
- /create
  - POST
  - Body = `User`
  - Create a new User
- /delete/{current_user_id}
  - DELETE
  - Delete a user / remove account
- /change-password/{current_user_id}
  - PUT
  - Body = {old_password, new_password}
  - change a users password
- /block/{blocked_id}
  - POST
  - Params = current_user_id
  - Block a user
- /unblock/{blocked_id}
  - DELETE
  - Params = current_user_id
  - Unblock a user
- /blocked/{current_user_id}
  - GET
  - Get current users blocked list

### Groups Endpoints

#### Lead with /groups

- /create
  - POST
  - Params = current_user_id
  - Body = `Group`
  - Create a new group
- /search
  - GET
  - Params = current_user_id, keyword
  - Search your groups
- /{current_user_id}/all
  - GET
  - Get all of a users groups
- /{group_id}
  - GET
  - Params = current_user_id
  - Get a specific group
- /{group_id}/availabilities
  - GET
  - Params = current_user_id
  - Get a list of group availabilities
- /{group_id}/availabilities/create
  - POST
  - Params = current_user_id
  - Body = `GroupAvailability`
  - Create a groupAvailability
- /{group_id}/availabilities/remove/{availability_id}
  - DELETE
  - Params = current_user_id
  - Remove a groupAvailability
- /{group_id}/events
  - GET
  - Params = current_user_id
  - Get all group events
- /{group_id}/events/create
  - POST
  - Params = current_user_id
  - Body = `Event`
  - Create an event
- /{group_id}/events/{event_id}
  - GET
  - Params = current_user_id
  - Get a specific event
- /{group_id}/events/{event_id}/update
  - PUT
  - Params = current_user_id
  - body = `Event`
  - Update an event
- /{group_id}/events/{event_id}/cancel
  - DELETE
  - Params = current_user_id
  - Delete an event
- /{group_id}/events/{event_id}/participants
  - GET
  - Params = current_user_id
  - Get participants for an event
- /{group_id}/events/{event_id}/join
  - POST
  - Params = current_user_id
  - Join an event
- /{group_id}/events/{event_id}/leave
  - DELETE
  - Params = current_user_id
  - Leave an event
- /{group_id}/members
  - GET
  - Param = current_user_id
  - Get a list of group members

#### Group Admin Only endpoints

- /{group_id}/update
  - PUT
  - Params = current_user_id
  - Body = `Group`
  - Update a group
- /{group_id}/members/add/{friend_id}
  - POST
  - Params = current_user_id
  - Add a friend to the group
- /{group_id}/members/remove/{member_id}
  - DELETE
  - Params = current_user_id
  - remove a group member
- /{group_id}/members/promote/{member_id}
  - POST
  - Params = current_user_id
  - Promote a group member to Admin
- /{group_id}/members/demote/{member_id}
  - POST
  - Params = current_user_id
  - Demote a current Admin

### Friendships Endpoints

#### Lead with /friendships

- /{current_user_id}
  - GET
  - Your friends List
- /request/{friend_id}
  - POST
  - Params = current_user_id
  - Request to add a friend
- /requests/{current_user_id}
  - GET
  - Get your friend requests
- /requests/accept/{request_id}
  - POST
  - Params = current_user_id
  - Accept a friend request
- /requests/deny/{request_id}
  - POST
  - Params = current_user_id
  - Deny a friend request

### Notifications Endpoints

#### Lead with /notifications

- /{current_user_id}
  - GET
  - Get all your notifications
- /get/{notification_id}
  - GET
  - Params = current_user_id
  - Get a specific notification
- /read/{notification_id}
  - POST
  - Params = current_user_id
  - Mark the notification as read
- /respond/{notification_id}
  - POST
  - Params = current_user_id
  - Respond to a notification

### Conversations Endpoints

#### lead with /conversations

- /{current_user_id}/all
  - GET
  - Get all you conversations
- /{conversation_id}
  - GET
  - Params = current_user_id
  - Get a specific conversation
- /create/{friend_id}
  - POST
  - Params = current_user_id
  - Create a conversation with a friend
- /{conversation_id}/messages
  - GET
  - Params = current_user_id
  - Get the messages of a conversation
- /{conversation_id}/send
  - POST
  - Params = current_user_id
  - Send a message to a conversation
- /group/{group_id}
  - GET
  - Params = current_user_id
  - Access a group conversation
- /group/{group_id}/send
  - POST
  - Params = current_user_id
  - Send a group message

### Admin Endpoints

#### Lead with /admin

- /users
  - GET
  - Params = admin_id
  - Get all Users
- /groups
  - GET
  - Params = admin_id
  - Get all groups
- /logs
  - GET
  - Params = admin_id
  - Get all Activity Logs
- /logs/clear
  - DELETE
  - Params = admin_id
  - Clear activity logs
