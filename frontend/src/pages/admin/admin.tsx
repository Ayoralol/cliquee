import {A} from "@solidjs/router";
import {Component} from "solid-js";

const Admin: Component = () => {
  return (
    <div>
      <A href="/admin/users">
        <button>See all users</button>
      </A>
      <A href="/admin/groups">
        <button>See all groups</button>
      </A>
      <A href="/admin/logs">
        <button>See all logs</button>
      </A>
    </div>
  );
};

export default Admin;
