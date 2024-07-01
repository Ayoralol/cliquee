import {Component, createSignal} from "solid-js";
import {useUser} from "../../context/UserProvider";
import {updateUserService} from "../../services/UserService";

const EditProfile: Component = () => {
  const {user, setUser} = useUser();

  const [username, setUsername] = createSignal(user()?.username);
  const [firstName, setFirstName] = createSignal(user()?.firstName);
  const [lastName, setLastName] = createSignal(user()?.lastName);
  const [email, setEmail] = createSignal(user()?.email);

  const handleSubmit = async (e: Event) => {
    e.preventDefault();
    const updateObject = {
      username: username(),
      first_name: firstName(),
      last_name: lastName(),
      email: email(),
    };
    try {
      const currentUser = user();
      const accessToken = currentUser?.accessToken;
      if (!accessToken) {
        throw new Error("No access token or user");
      }
      if (
        !updateObject.username ||
        !updateObject.first_name ||
        !updateObject.last_name ||
        !updateObject.email
      ) {
        throw new Error("All fields are required");
      }
      const response = await updateUserService(updateObject, accessToken);
      console.log(response);
      setUser({
        ...currentUser,
        username: updateObject.username,
        firstName: updateObject.first_name,
        lastName: updateObject.last_name,
        email: updateObject.email,
      });
      localStorage.setItem("user", JSON.stringify(currentUser));
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div class="row">
      <div class="cont-2">
        <form>
          <h3>Username</h3>
          <input
            type="text"
            value={username()}
            onChange={(e) => setUsername((e.target as HTMLInputElement).value)}
          />
          <h3>First Name</h3>
          <input
            type="text"
            value={firstName()}
            onChange={(e) => setFirstName((e.target as HTMLInputElement).value)}
          />
          <h3>Last Name</h3>
          <input
            type="text"
            value={lastName()}
            onChange={(e) => setLastName((e.target as HTMLInputElement).value)}
          />
          <h3>Email</h3>
          <input
            type="text"
            value={email()}
            onChange={(e) => setEmail((e.target as HTMLInputElement).value)}
          />
          <button onClick={(e) => handleSubmit(e)}>Save</button>
        </form>
      </div>
    </div>
  );
};

export default EditProfile;
