import {Component} from "solid-js";
import LoginModal from "./LoginModal";
import {A} from "@solidjs/router";
import {useUser} from "../context/UserProvider";

const Navigation: Component = () => {
  const {user, isLoggedIn, setIsLoggedIn} = useUser();

  const handleLogout = () => {
    setIsLoggedIn(false);
  };

  return (
    <div class="nav">
      <div class="nav-btns">
        {isLoggedIn() && (
          <A href="/">
            <button>Home</button>
          </A>
        )}
        {isLoggedIn() && (
          <A href="/profile">
            <button>Profile</button>
          </A>
        )}
        {isLoggedIn() && (
          <A href="/groups">
            <button>Groups</button>
          </A>
        )}
        {isLoggedIn() && (
          <A href="/search">
            <button>Search</button>
          </A>
        )}
        {!isLoggedIn() && <LoginModal />}
        {user()?.role == "ADMIN" && (
          <A href="/admin">
            <button>Admin</button>
          </A>
        )}
        {isLoggedIn() && <button onClick={() => handleLogout()}>Logout</button>}
      </div>
    </div>
  );
};

export default Navigation;
