# Welcome to Cliquee

Tired of messaging the group chat and not being able to find a time to hangout through all of the other messages?

Cliquee will be for you!

Cliquee will be a website where people can organise outings depending on availability, easily and quickly.

Just enter when you are available and Cliquee will do the rest!

### Changelog

#### 23/07

- resuming work on this project with this updated readme

### 02/07 - 22/07 TIME OFF

#### 01/07

- Converted DB to postgreSQL
- wrote frontend notification, conversation, admin, and group services
- nav updates
- endpoint bug fixes

#### 30/06

- Added proper login and register frontend
- Added services allowing my JWT tokens to work
- added userServices and fixed endpoints to return id's

#### 29/06

- Started Frontend files
- Added landing and register/login to test
- fixed errors on username/email
- Documentation updates

#### 28/06

- Added initial JWT Authentication
- Fixed all endpoints to use JWT
- Fixed endpoint documentation to reflect JWT changes

#### 26/06

- Fixed Friend Request Service to actually add friend requests
- wrote conversation, group message, and group notifications
- Implemented audit logs after most actions
- added password hashing with werkzeug

#### 25/06

- Finished converting all endpoints into flask
- documentation changes
- completed all services

#### 24/06

- Started backend conversion into Flask/Python
- created models in flask
- created route files
- Started users endpoints

### Java backend below this point (old backend)

#### 21/06

- made new DTO's for handling return
- Refactored notification handling
- fixed updated service methods removing startup code errors

#### 20/06

- changed spring security to permitAll for now
- documentation update
- proper endpoint updates
- created notification method

#### 19/06

- Security changes until 200 OK return

#### 18/06

- Security updates
- group endpoints and group conversation updates
- changed id to UUID
- added create availability

#### 17/06

- finished user + friendship service and controller
- Notification service and controller
- blockService and added blocking/unblocking to UserController
- Changed search to not display blocked users
- conversations service and controller
- groupmessage service
- adminController and admin security
- started some security config

#### 16/06

- added application-template.properties
- Basic backend setup
- created relations between the tables + embedded id's
- Entity type fixes
- Started UserService and UserController

#### 14/06 - 15/06

- Started project
- Initial file setups
- created initial backend schema
- made table layouts
