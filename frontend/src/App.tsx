import "./App.css";
import {User, loginService, registerService} from "./services/userService";

function App() {
  const user1: User = {
    username: "user1",
    email: "user@gmail.com",
    password: "password",
    firstName: "User",
    lastName: "One",
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

    return (
      <>
        <button onClick={handleClick}>REGISTER BLANK USER</button>
        <button onClick={handleLogin}>LOGIN SAME USER</button>
      </>
    );
  };
}
export default App;
