### Returns of the fetches to the backend

#### ALL errors SHOULD return {error: "error message"}, 4xx

#### /users endpoints

- /login -> {username, first_name, last_name, access_token}
- /{user_id} -> {username, first_name, last_name}
- /username/{username} -> {username, first_name, last_name}
- /email/{email} -> {username, first_name, last_name}
- /search -> users: {{username, first_name, last_name}}
- /update -> {message}
- /create -> {message}
- /delete -> {message}
- /change_password -> {message}
- /block/{blocked_id} -> {message}
- /unblock/{blocked_id} -> {message}
- /blocked -> blocked_users: {{username, first_name, last_name}}

#### /groups endpoints

- /create -> {message}
- /search -> groups: {{name, description}}
- /all -> groups: {{name, description}}
- /{group_id} -> {name, description}
- /{group_id}/availabilities -> availabilities: {{username, day, start_time, end_time}}
- /{group_id}/user_availabilities -> availabilities: {{day, start_time, end_time}}
- /{group_id}/availabilities/create -> {message}
- /{group_id}/availabilities/remove/{availability_id} -> {message}
- /{group_id}/events -> events: {{name, description, location, start_time, end_time}}
- /{group_id}/events/create -> {message}
- /{group_id}/events/{event_id} -> {name, description, location, start_time, end_time}
- /{group_id}/events/{event_id}/update -> {message}
- /{group_id}/events/{event_id}/cancel -> {message}
- /{group_id}/events/{event_id}/participants -> participants: {{username}}
- /{group_id}/events/{event_id}/join -> {message}
- /{group_id}/events/{event_id}/leave -> {message}
- /{group_id}/members -> members: {{username, role}}
- /{group_id}/update -> {message}
- /{group_id}/members/add/{friend_id} -> {message}
- /{group_id}/members/remove/{friend_id} -> {message}
- /{group_id}/members/promote/{friend_id} -> {message}
- /{group_id}/members/demote/{friend_id} -> {message}

#### /friendships endpoints

- /all -> friends: {{username, first_name, last_name}}
- /request/{friend_id} -> {message}
- /requests -> friend_requests: {{request_id, username, first_name, last_name}}
- /requests/accept/{request_id} -> {message}
- /requests/deny/{request_id} -> {message}

#### /notifications endpoints

- /all ->notifications: {{id, sender, type, related_id, message, is_read, responded}}
- /get/{notification_id} -> {id, sender, type, related_id, message, is_read, responded}
- /read/{notification_id} -> {message}
- /respond/{notification_id} -> {message}

#### /conversations endpoints

- /all -> conversations {{conversation_id, username, message}}
- /{conversation_id} -> {conversation_id, friend_username, messages: {{username, message}}}
- /create/{friend_id} -> {message}
- /{conversation_id}/send -> {message}
- /group/{group_id} -> {group_id, group_name, messages: {{username, message}}}
- /group/{group_id}/send -> {message}

#### /admin endpoints

- /users -> users: {{id, username, email, first_name, last_name, created_at, updated_at}}
- /groups -> groups: {{id, name, description, created_at, updated_at, members: {{member_id, member_username}}}}
- /logs -> logs: {{id, user_id, action, details, created_at}}
- /logs/clear -> {message}
