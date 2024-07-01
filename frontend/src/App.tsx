import {Component} from "solid-js";
import "./App.css";
import Navigation from "./components/Navigation";

const App: Component<any> = (props) => {
  return (
    <div class="page">
      <div class="container flex-column">
        <Navigation />
        <div class="cont">{props.children}</div>
      </div>
    </div>
  );
};
export default App;
