import {createContext, createEffect, createSignal, useContext} from "solid-js";
import {User} from "../services/UserService";

interface UserContext {
  user: () => User | null;
  setUser: (user: User | null) => void;
  isLoggedIn: () => boolean;
  setIsLoggedIn: (value: boolean) => void;
}

const UserContext = createContext<UserContext>();

export function UserProvider(props: {children: any}) {
  const [user, setUser] = createSignal<User | null>(
    JSON.parse(localStorage.getItem("user") || "null")
  );
  const [isLoggedIn, setIsLoggedIn] = createSignal<boolean>(
    localStorage.getItem("user") ? true : false
  );

  createEffect(() => {
    if (isLoggedIn()) {
      localStorage.setItem("user", JSON.stringify(user()));
    } else {
      localStorage.removeItem("user");
    }
  });

  return (
    <UserContext.Provider value={{user, setUser, isLoggedIn, setIsLoggedIn}}>
      {props.children}
    </UserContext.Provider>
  );
}

export function useUser() {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error("User context not found");
  }
  return context;
}
