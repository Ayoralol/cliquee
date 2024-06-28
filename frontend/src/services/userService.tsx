export interface User {
  id?: number;
  username?: string;
  email?: string;
  password?: string;
  firstName?: string;
  lastName?: string;
  created_at?: string;
}

export const loginService = async (username: string, password: string) => {
  try {
    const response = await fetch("http://localhost:5000/users/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({username: username, password: password}),
    });
    return response.json;
  } catch (error) {
    throw error;
  }
};

export const registerService = async (user: User) => {
  try {
    const response = await fetch("http://localhost:5000/users/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: user.username,
        email: user.email,
        password: user.password,
        first_name: user.firstName,
        last_name: user.lastName,
      }),
    });
    return response.json;
  } catch (error) {
    throw error;
  }
};
