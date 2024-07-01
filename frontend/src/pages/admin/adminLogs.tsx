import {Component, createSignal, onMount} from "solid-js";
import {adminGetLogsService} from "../../services/AdminService";
import {useUser} from "../../context/UserProvider";

type Log = {
  id: number;
  user_id: number;
  action: string;
  details: string;
  createdAt: string;
};

const AdminLogs: Component = () => {
  const {user} = useUser();
  const [logs, setLogs] = createSignal<Log[]>([]);

  const getLogs = async () => {
    try {
      const accessToken = user()?.accessToken;
      if (!accessToken) {
        throw new Error("No access token");
      }
      const response = await adminGetLogsService(accessToken);
      setLogs(response.logs);
    } catch (error) {
      console.error(error);
    }
  };

  onMount(() => {
    getLogs();
  });

  return (
    <div>
      {logs().length > 0 ? (
        logs().map((log) => (
          <div>
            <p>ID: {log.id}</p>
            <p>User ID: {log.user_id}</p>
            <p>Action: {log.action}</p>
            <p>Details: {log.details}</p>
            <p>Created At: {log.createdAt}</p>
          </div>
        ))
      ) : (
        <p>No logs available</p>
      )}
    </div>
  );
};

export default AdminLogs;
