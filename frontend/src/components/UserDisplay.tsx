import {Component} from "solid-js";

type User = {
  id: number;
  username: string;
  first_name: string;
  last_name: string;
};

type UserDisplayProps = {
  users: User[];
};

const UserDisplay: Component<UserDisplayProps> = ({users}) => {
  return (
    <div class="row">
      {users.map((user) => (
        <div class="cont-2 flex-column center">
          <div class="all-margin">
            <h3>Username</h3>
            <p>{user.username}</p>
            <h3>First Name</h3>
            <p>{user.first_name}</p>
            <h3>Last Name</h3>
            <p>{user.last_name}</p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default UserDisplay;
