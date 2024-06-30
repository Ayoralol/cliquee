export const getAllNotificationsService = async (accessToken: string) => {
  try {
    const response = await fetch("http://localhost:5000/notifications/all", {
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

export const getNotificationService = async (
  notificationId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/notifications/${notificationId}`,
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

export const readNotificationService = async (
  notificationId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/notifications/read/${notificationId}`,
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

export const respondToNotificationService = async (
  notificationId: number,
  userResponse: string,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/notifications/respond/${notificationId}`,
      {
        method: "POST",
        headers: {
          authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({response: userResponse}),
      }
    );
    return await response.json();
  } catch (error) {
    throw error;
  }
};
