## Endpoint Address Documentation

### Users Endpoints

#### Lead with /users

- /login
  - POST
  - Body = username, password
  - Logs in user and returns `jwt_token` AS `access_token` + the user
- /{user_id}
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get a users profile
- /username/{username}
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get a user by username
- /email/{email}
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get a user by email
- /search
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Params = keyword
  - Search for users
- /update
  - PUT
  - headers = Authorisation: Bearer `jwt_token`
  - Body = `User`
  - Update your profile
- /create
  - POST
  - Body = `User`
  - Create a new User
- /delete
  - DELETE
  - headers = Authorisation: Bearer `jwt_token`
  - Delete a user / remove account
- /change-password
  - PUT
  - headers = Authorisation: Bearer `jwt_token`
  - Body = {old_password, new_password}
  - change a users password
- /block/{blocked_id}
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Block a user
- /unblock/{blocked_id}
  - DELETE
  - headers = Authorisation: Bearer `jwt_token`
  - Unblock a user
- /blocked
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get current users blocked list

### Groups Endpoints

#### Lead with /groups

- /create
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Body = `Group`
  - Create a new group
- /search
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Params = keyword
  - Search your groups
- /all
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get all of a users groups
- /{group_id}
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get a specific group
- /{group_id}/availabilities
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get a list of group availabilities
- /{group_id}/user_availabilities
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get a list of your own availabilities
- /{group_id}/availabilities/create
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Body = `GroupAvailability`
  - Create a groupAvailability
- /{group_id}/availabilities/remove/{availability_id}
  - DELETE
  - headers = Authorisation: Bearer `jwt_token`
  - Remove a groupAvailability
- /{group_id}/events
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get all group events
- /{group_id}/events/create
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Body = `Event`
  - Create an event
- /{group_id}/events/{event_id}
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get a specific event
- /{group_id}/events/{event_id}/update
  - PUT
  - headers = Authorisation: Bearer `jwt_token`
  - body = `Event`
  - Update an event
- /{group_id}/events/{event_id}/cancel
  - DELETE
  - headers = Authorisation: Bearer `jwt_token`
  - Delete an event
- /{group_id}/events/{event_id}/participants
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get participants for an event
- /{group_id}/events/{event_id}/join
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Join an event
- /{group_id}/events/{event_id}/leave
  - DELETE
  - headers = Authorisation: Bearer `jwt_token`
  - Leave an event
- /{group_id}/members
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get a list of group members

#### Group Admin Only endpoints

- /{group_id}/update
  - PUT
  - headers = Authorisation: Bearer `jwt_token`
  - Body = `Group`
  - Update a group
- /{group_id}/members/add/{friend_id}
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Add a friend to the group
- /{group_id}/members/remove/{member_id}
  - DELETE
  - headers = Authorisation: Bearer `jwt_token`
  - remove a group member
- /{group_id}/members/promote/{member_id}
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Promote a group member to Admin
- /{group_id}/members/demote/{member_id}
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Demote a current Admin

### Friendships Endpoints

#### Lead with /friendships

- /all
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Your friends List
- /request/{friend_id}
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Request to add a friend
- /requests
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get your friend requests
- /requests/accept/{request_id}
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Accept a friend request
- /requests/deny/{request_id}
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Deny a friend request

### Notifications Endpoints

#### Lead with /notifications

- /all
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get all your notifications
- /get/{notification_id}
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get a specific notification
- /read/{notification_id}
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Mark the notification as read
- /respond/{notification_id}
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Respond to a notification

### Conversations Endpoints

#### lead with /conversations

- /all
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get all you conversations
- /{conversation_id}
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Get a specific conversation
- /create/{friend_id}
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Create a conversation with a friend
- /{conversation_id}/send
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Body = "message"
  - Send a message to a conversation
- /group/{group_id}
  - GET
  - headers = Authorisation: Bearer `jwt_token`
  - Access a group conversation
- /group/{group_id}/send
  - POST
  - headers = Authorisation: Bearer `jwt_token`
  - Body = "message"
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
