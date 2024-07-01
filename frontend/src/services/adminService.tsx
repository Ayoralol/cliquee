export const adminGetUsersService = async (accessToken: string) => {
  try {
    const response = await fetch("http://localhost:5000/admin/users", {
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

export const adminGetGroupsService = async (accessToken: string) => {
  try {
    const response = await fetch("http://localhost:5000/admin/groups", {
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

export const adminGetLogsService = async (accessToken: string) => {
  try {
    const response = await fetch("http://localhost:5000/admin/logs", {
      method: "GET",
      headers: {
        authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
    });
    console.log("here");
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const adminDeleteLogsService = async (accessToken: string) => {
  try {
    const response = await fetch("http://localhost:5000/admin/logs/clear", {
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
