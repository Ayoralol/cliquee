import {A} from "@solidjs/router";
import {Component} from "solid-js";

const Landing: Component = () => {
  return (
    <div class="cont-1 flex-column landing-page center">
      <h1>Welcome to Cliquee!</h1>
      <p class="text-center">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Error quam
        perferendis hic nemo nostrum repellendus ea qui fuga. Nesciunt dicta
        accusantium alias beatae. Consectetur repellendus soluta dolorum? Nulla
        necessitatibus, mollitia totam fuga eaque sed consequatur! Animi cum
        amet praesentium voluptas!
      </p>
      <A href="/register" class="marg-20">
        <button>Create Account!</button>
      </A>
    </div>
  );
};

export default Landing;
