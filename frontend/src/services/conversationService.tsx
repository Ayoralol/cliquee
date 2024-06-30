export const getAllConversationsService = async (accessToken: string) => {
  try {
    const response = await fetch("http://localhost:5000/conversations/all", {
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

export const getConversationService = async (
  conversationId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/conversations/${conversationId}`,
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

export const createConversationService = async (
  friendId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/conversations/create/${friendId}`,
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

export const sendMessageService = async (
  conversationId: number,
  message: string,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/conversations/${conversationId}/send`,
      {
        method: "POST",
        headers: {
          authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({message}),
      }
    );
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const getGroupConversationService = async (
  groupId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/conversations/group/${groupId}`,
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

export const sendGroupMessageService = async (
  groupId: number,
  message: string,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/conversations/group/${groupId}/send`,
      {
        method: "POST",
        headers: {
          authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({message}),
      }
    );
    return await response.json();
  } catch (error) {
    throw error;
  }
};
