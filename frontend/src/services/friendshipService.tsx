export const getAllFriendshipService = async (accessToken: string) => {
  try {
    const response = await fetch("http://localhost:5000/friendships/all", {
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

export const sendFriendRequestService = async (
  friendId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/friendships/request/${friendId}`,
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

export const getFriendRequestsService = async (accessToken: string) => {
  try {
    const response = await fetch("http://localhost:5000/friendships/requests", {
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

export const acceptFriendRequestService = async (
  requestId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/friendships/requests/accept/${requestId}`,
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

export const denyFriendRequestService = async (
  requestId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/friendships/requests/deny/${requestId}`,
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
