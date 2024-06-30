export interface User {
  id?: number;
  username?: string;
  email?: string;
  password?: string;
  firstName?: string;
  lastName?: string;
  created_at?: string;
  updated_at?: string;
}

export interface LoggedUser {
  id: number;
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

export const getUserService = async (userId: number, accessToken: string) => {
  try {
    const response = await fetch(`http://localhost:5000/users/${userId}`, {
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

export const updateUserService = async (user: User, accessToken: string) => {
  try {
    const response = await fetch(`http://localhost:5000/users/update`, {
      method: "PUT",
      headers: {
        authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    });
    return await response.json();
  } catch (error) {}
};

export const deleteUserService = async (accessToken: string) => {
  try {
    const response = await fetch(`http://localhost:5000/users/delete`, {
      method: "DELETE",
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

export const changePasswordService = async (
  oldPassword: string,
  newPassword: string,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/users/change-password`,
      {
        method: "PUT",
        headers: {
          authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({oldPassword, newPassword}),
      }
    );
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const blockUserService = async (
  blockedId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/users/block/${blockedId}`,
      {
        method: "POST",
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

export const unblockUserService = async (
  blockedId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/users/unblock/${blockedId}`,
      {
        method: "POST",
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

export const getBlockedUsersService = async (accessToken: string) => {
  try {
    const response = await fetch(`http://localhost:5000/users/blocked`, {
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
