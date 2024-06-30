import {Component} from "solid-js";
import "./App.css";
import Navigation from "./components/Navigation";

const App: Component<any> = (props) => {
  return (
    <div class="container">
      <Navigation />
      {props.children}
    </div>
  );
};
export default App;
