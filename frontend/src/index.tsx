/* @refresh reload */
import {render} from "solid-js/web";
import "./index.css";
import App from "./App";
import {UserProvider} from "./context/UserProvider";
import {Route, Router} from "@solidjs/router";
import Landing from "./pages/Landing";
import Register from "./pages/users/Register";
import Friends from "./pages/users/Friends";
import Messages from "./pages/users/Messages";
import FriendProfile from "./pages/users/FriendProfile";
import Profile from "./pages/users/Profile";
import EditProfile from "./pages/users/EditProfile";
import Search from "./pages/Search";
import GroupsList from "./pages/groups/GroupsList";
import CreateGroup from "./pages/groups/CreateGroup";
import Group from "./pages/groups/Group";
import EditGroup from "./pages/groups/EditGroup";
import GroupEventsList from "./pages/groups/GroupEventsList";
import CreateGroupEvent from "./pages/groups/CreateGroupEvent";
import GroupEvent from "./pages/groups/GroupEvent";
import EditGroupEvent from "./pages/groups/EditGroupEvent";
import GroupMembers from "./pages/groups/GroupMembers";
import AddGroupMember from "./pages/groups/AddGroupMember";
import Admin from "./pages/admin/Admin";
import AdminUsers from "./pages/admin/AdminUsers";
import AdminGroups from "./pages/admin/AdminGroups";
import AdminLogs from "./pages/admin/AdminLogs";

render(
  () => (
    <UserProvider>
      <Router root={App}>
        <Route path="/" component={Landing} />
        <Route path="/register" component={Register} />
        <Route path="/friends" component={Friends} />
        <Route path="/messages" component={Messages} />
        <Route path="/user/{id}" component={FriendProfile} />
        <Route path="/profile" component={Profile} />
        <Route path="/profile/edit" component={EditProfile} />
        <Route path="/search" component={Search} />
        <Route path="/groups" component={GroupsList} />
        <Route path="/groups/create" component={CreateGroup} />
        <Route path="/groups/{id}" component={Group} />
        <Route path="/groups/{id}/edit" component={EditGroup} />
        <Route path="/groups/{id}/events" component={GroupEventsList} />
        <Route path="/groups/{id}/events/create" component={CreateGroupEvent} />
        <Route path="/groups/{id}/events/{eventId}" component={GroupEvent} />
        <Route
          path="/groups/{id}/events/{eventId}/edit"
          component={EditGroupEvent}
        />
        <Route path="/groups/{id}/members" component={GroupMembers} />
        <Route path="/groups/{id}/add-member" component={AddGroupMember} />
        <Route path="/admin" component={Admin} />
        <Route path="/admin/users" component={AdminUsers} />
        <Route path="/admin/groups" component={AdminGroups} />
        <Route path="/admin/logs" component={AdminLogs} />
      </Router>
    </UserProvider>
  ),
  document.getElementById("root") as HTMLElement
);
