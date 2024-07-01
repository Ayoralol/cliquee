import {Component, createSignal} from "solid-js";
import {User, registerService} from "../../services/UserService";

const Register: Component = () => {
  const [username, setUsername] = createSignal("");
  const [email, setEmail] = createSignal("");
  const [password, setPassword] = createSignal("");
  const [firstName, setFirstName] = createSignal("");
  const [lastName, setLastName] = createSignal("");
  const [showPassword, setShowPassword] = createSignal(false);

  const handleSubmit = async (e: Event) => {
    e.preventDefault();
    console.log(username(), email(), password(), firstName(), lastName());
    try {
      const newUser: User = {
        username: username(),
        email: email(),
        password: password(),
        firstName: firstName(),
        lastName: lastName(),
      };
      const response = await registerService(newUser);
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  const handleShowPassword = (e: Event) => {
    e.preventDefault();
    setShowPassword(!showPassword());
  };

  return (
    <div>
      <form class="flex-column">
        <label>Username</label>
        <input
          type="text"
          onChange={(e) => setUsername(e.target.value)}
          value={username()}
        />
        <label>First Name</label>
        <input
          type="text"
          onChange={(e) => setFirstName(e.target.value)}
          value={firstName()}
        />
        <label>Last Name</label>
        <input
          type="text"
          onChange={(e) => setLastName(e.target.value)}
          value={lastName()}
        />
        <label>Email</label>
        <input
          type="email"
          onChange={(e) => setEmail(e.target.value)}
          value={email()}
        />
        <label>Password</label>
        <input
          type={!showPassword() ? "password" : "text"}
          onChange={(e) => setPassword(e.target.value)}
          value={password()}
        />
        <button onClick={(e) => handleShowPassword(e)}>Show Password</button>
        <button class="marg-20" onClick={(e) => handleSubmit(e)}>
          Register!
        </button>
      </form>
    </div>
  );
};

export default Register;
