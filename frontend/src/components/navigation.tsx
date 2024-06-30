import {Component} from "solid-js";
import LoginModal from "./LoginModal";
import SearchBar from "./SearchBar";
import {A} from "@solidjs/router";

const Navigation: Component = () => {
  return (
    <div class="nav">
      <A href="/">
        <button>Home</button>
      </A>
      <A href="/profile">
        <button>Profile</button>
      </A>
      <A href="/groups">
        <button>Groups</button>
      </A>
      <A href="/admin">
        <button>Admin</button>
      </A>
      <SearchBar />
      <LoginModal />
    </div>
  );
};

export default Navigation;
