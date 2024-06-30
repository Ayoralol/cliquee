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
      <SearchBar />
      <LoginModal />
    </div>
  );
};

export default Navigation;
