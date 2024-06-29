/* @refresh reload */
import {render} from "solid-js/web";
import "./index.css";
import App from "./App";
import {UserProvider} from "./context/userProvider";
import {Route, Router} from "@solidjs/router";
import Landing from "./pages/Landing";

render(
  () => (
    <UserProvider>
      <Router root={App}>
        <Route path="/" component={Landing} />
      </Router>
    </UserProvider>
  ),
  document.getElementById("root") as HTMLElement
);
