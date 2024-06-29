import {Component} from "solid-js";
import {User, loginService, registerService} from "../services/userService";

const Landing: Component = () => {
  const user1: User = {
    username: "user2",
    email: "user2@gmail.com",
    password: "password",
    firstName: "User",
    lastName: "Two",
  };

  const handleClick = async () => {
    try {
      const response = await registerService(user1);
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  const handleLogin = async () => {
    try {
      const response = await loginService(user1.username!, user1.password!);
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };
  return (
    <div>
      <button onClick={handleClick}>REGISTER BLANK USER</button>
      <button onClick={handleLogin}>LOGIN SAME USER</button>
    </div>
  );
};

export default Landing;
