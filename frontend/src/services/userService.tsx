export interface User {
  id?: number;
  username?: string;
  email?: string;
  password?: string;
  firstName?: string;
  lastName?: string;
  created_at?: string;
}

export interface LoggedUser {
  username: string;
  firstName: string;
  lastName: string;
  accessToken: string;
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
    return await response.json();
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
    return response.json();
  } catch (error) {
    throw error;
  }
};

export const getUserService = async (id: number, accessToken: string) => {
  try {
    const response = await fetch(`http://localhost:5000/users/${id}`, {
      method: "GET",
      headers: {
        authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
    });
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const getUserByUsernameService = async (
  username: string,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/users/username/${username}`,
      {
        method: "GET",
        headers: {
          authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
      }
    );
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const getUserByEmailService = async (
  email: string,
  accessToken: string
) => {
  try {
    const response = await fetch(`http://localhost:5000/users/email/${email}`, {
      method: "GET",
      headers: {
        authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
    });
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const searchUsersService = async (
  query: string,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/users/search?keyword=${query}`,
      {
        method: "GET",
        headers: {
          authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
      }
    );
    return await response.json();
  } catch (error) {
    throw error;
  }
};
