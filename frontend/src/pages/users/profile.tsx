import {Component} from "solid-js";
import {useUser} from "../../context/UserProvider";
import {A} from "@solidjs/router";

const Profile: Component = () => {
  const {user} = useUser();
  return (
    <div class="row">
      <div class="cont-2 flex-column center">
        <div class="all-margin">
          <h3>Username</h3>
          <p>{user()?.username}</p>
          <h3>First Name</h3>
          <p>{user()?.firstName}</p>
          <h3>Last Name</h3>
          <p>{user()?.lastName}</p>
          <h3>Email</h3>
          <p>{user()?.email}</p>
          <A href="/profile/edit">
            <button>Edit Profile</button>
          </A>
        </div>
      </div>
    </div>
  );
};

export default Profile;
