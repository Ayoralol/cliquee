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
        id: response.id,
        username: response.username,
        firstName: response.first_name,
        lastName: response.last_name,
        email: response.email,
        role: response.role,
        accessToken: response.access_token,
      };
      setUser(loggedUser);
      setIsLoggedIn(true);
      setShowModal(false);
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
      <button onClick={() => setShowModal(!showModal())}>Login</button>
      {showModal() && (
        <div class="modal-back" onClick={() => setShowModal(false)}>
          <div class="login-modal" onClick={(e) => e.stopPropagation()}>
            <div class="login-modal-content">
              <button onClick={() => setShowModal(false)} class="close">
                &times;
              </button>
              <form class="flex-column">
                <label>Username</label>
                <input
                  type="text"
                  onChange={(e) =>
                    setUsername((e.target as HTMLInputElement).value)
                  }
                  value={username()}
                />
                <label>Password</label>
                <input
                  type={!showPassword() ? "password" : "text"}
                  onChange={(e) =>
                    setPassword((e.target as HTMLInputElement).value)
                  }
                />
                <button
                  onClick={(e) => handleShowPassword(e)}
                  value={password()}>
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
        </div>
      )}
    </div>
  );
};

export default LoginModal;
