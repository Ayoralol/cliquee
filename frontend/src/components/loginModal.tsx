import {Component, createSignal} from "solid-js";
import {loginService} from "../services/UserService";
import {useUser} from "../context/UserProvider";
import {A} from "@solidjs/router";

const LoginModal: Component = () => {
  const [showModal, setShowModal] = createSignal(false);
  const [username, setUsername] = createSignal("");
  const [password, setPassword] = createSignal("");
  const [showPassword, setShowPassword] = createSignal(false);

  const {setUser, setIsLoggedIn} = useUser();

  const handleSubmit = async (e: Event) => {
    e.preventDefault();
    console.log(username(), password());
    try {
      const response = await loginService(username(), password());
      console.log(response);
      const loggedUser = {
        username: response.username,
        firstName: response.first_name,
        lastName: response.last_name,
        accessToken: response.access_token,
      };
      setUser(loggedUser);
      setIsLoggedIn(true);
      setShowModal(false);
    } catch (error) {
      console.error(error);
    }
  };

  const handleUsername = (e: Event) => {
    setUsername((e.target as HTMLInputElement).value);
  };

  const handlePassword = (e: Event) => {
    setPassword((e.target as HTMLInputElement).value);
  };

  const handleShowPassword = (e: Event) => {
    e.preventDefault();
    setShowPassword(!showPassword());
  };

  return (
    <div>
      <button onClick={() => setShowModal(!showModal())}>Login</button>
      {showModal() && (
        <div class="login-modal">
          <div class="login-modal-content">
            <button onClick={() => setShowModal(false)} class="close">
              &times;
            </button>
            <form class="flex-column">
              <label>Username</label>
              <input type="text" onChange={(e) => handleUsername(e)} />
              <label>Password</label>
              <input
                type={!showPassword() ? "password" : "text"}
                onChange={(e) => handlePassword(e)}
              />
              <button onClick={(e) => handleShowPassword(e)}>
                Show Password
              </button>
              <button class="marg-20" onClick={(e) => handleSubmit(e)}>
                Login!
              </button>
            </form>
            <div>
              Dont have an account?{" "}
              <A href="/register" onClick={() => setShowModal(false)}>
                <button>Create One!</button>
              </A>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default LoginModal;
