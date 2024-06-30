import {Component, createSignal} from "solid-js";
import {getUserByUsernameService} from "../services/UserService";
import {useUser} from "../context/UserProvider";

interface displayUser {
  username: string;
  firstName: string;
  lastName: string;
}

const Landing: Component = () => {
  const [display, setDisplay] = createSignal<displayUser | null>(null);
  const {user} = useUser() ?? {};

  const loadUser = async (e: Event) => {
    e.preventDefault();
    try {
      const currentUser = user();
      const accessToken = currentUser?.accessToken;
      if (!accessToken) {
        throw new Error("No access token");
      }
      const response = await getUserByUsernameService("user2", accessToken);
      setDisplay({
        username: response.username,
        firstName: response.first_name,
        lastName: response.last_name,
      });
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      LANDING PAGE
      <button onClick={(e) => loadUser(e)}>LOAD USER INFO</button>
      {display() !== null && (
        <div>
          <p>Username: {display()?.username}</p>
          <p>First Name: {display()?.firstName}</p>
          <p>Last Name: {display()?.lastName}</p>
        </div>
      )}
    </div>
  );
};

export default Landing;
