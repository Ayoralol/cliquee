export interface createEvent {
  name: string;
  description: string;
  location: string;
  start_time: string;
  end_time: string;
}

export const createGroupService = async (
  name: string,
  description: string,
  accessToken: string
) => {
  try {
    const response = await fetch("http://localhost:5000/groups/create", {
      method: "POST",
      headers: {
        authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({name, description}),
    });
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const searchGroupsService = async (
  keyword: string,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/search?keyword=${keyword}`,
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

export const getAllGroupsService = async (accessToken: string) => {
  try {
    const response = await fetch("http://localhost:5000/groups/all", {
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

export const getGroupService = async (groupId: number, accessToken: string) => {
  try {
    const response = await fetch(`http://localhost:5000/groups/${groupId}`, {
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

export const getGroupAvailabilitiesService = async (
  groupId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/availabilities`,
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

export const getUserAvailabilitiesService = async (
  groupId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/user_availabilities`,
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

export const createAvailabilityService = async (
  groupId: number,
  day: string,
  start_time: string,
  end_time: string,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/availabilities/create`,
      {
        method: "POST",
        headers: {
          authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({day, start_time, end_time}),
      }
    );
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const removeAvailabilityService = async (
  groupId: number,
  availabilityId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/availabilities/remove/${availabilityId}`,
      {
        method: "DELETE",
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

export const getGroupEventsService = async (
  groupId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/events`,
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

export const createEventService = async (
  groupId: number,
  event: createEvent,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/events/create`,
      {
        method: "POST",
        headers: {
          authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: event.name,
          description: event.description,
          location: event.location,
          start_time: event.start_time,
          end_time: event.end_time,
        }),
      }
    );
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const getEventService = async (
  groupId: number,
  eventId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/events/${eventId}`,
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

export const updateEventService = async (
  groupId: number,
  eventId: number,
  event: createEvent,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/events/${eventId}/update`,
      {
        method: "PUT",
        headers: {
          authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: event.name,
          description: event.description,
          location: event.location,
          start_time: event.start_time,
          end_time: event.end_time,
        }),
      }
    );
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const cancelEventService = async (
  groupId: number,
  eventId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/events/${eventId}/cancel`,
      {
        method: "DELETE",
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

export const getEventParticipantsService = async (
  groupId: number,
  eventId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/events/${eventId}/participants`,
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

export const joinGroupEventService = async (
  groupId: number,
  eventId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/events/${eventId}/join`,
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

export const leaveGroupEventService = async (
  groupId: number,
  eventId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/events/${eventId}/leave`,
      {
        method: "DELETE",
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

export const getGroupMembersService = async (
  groupId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/members`,
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

export const updateGroupService = async (
  groupId: number,
  name: string,
  description: string,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/update`,
      {
        method: "PUT",
        headers: {
          authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({name, description}),
      }
    );
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const addMemberService = async (
  groupId: number,
  friendId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/members/add/${friendId}`,
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

export const removeMemberService = async (
  groupId: number,
  memberId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/members/remove/${memberId}`,
      {
        method: "DELETE",
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

export const promoteMemberService = async (
  groupId: number,
  memberId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/members/promote/${memberId}`,
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

export const demoteMemberService = async (
  groupId: number,
  memberId: number,
  accessToken: string
) => {
  try {
    const response = await fetch(
      `http://localhost:5000/groups/${groupId}/members/demote/${memberId}`,
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
