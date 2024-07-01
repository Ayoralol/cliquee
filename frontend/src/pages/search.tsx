import {Component, createSignal} from "solid-js";
import {searchUsersService} from "../services/UserService";
import {useUser} from "../context/UserProvider";
import UserDisplay from "../components/UserDisplay";

const Search: Component = () => {
  const [query, setQuery] = createSignal<string>("");
  const [users, setUsers] = createSignal([]);
  const [loading, setLoading] = createSignal(false);

  const {user} = useUser();

  const handleSearch = async (e: Event) => {
    e.preventDefault();
    try {
      setLoading(true);
      const accessToken = user()?.accessToken;
      if (!accessToken) {
        throw new Error("No access token");
      }
      const response = await searchUsersService(query(), accessToken);
      setUsers(response.users);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleQuery = (e: Event) => {
    setQuery((e.target as HTMLInputElement).value);
  };

  return (
    <div class="row">
      <form>
        <input
          type="text"
          placeholder="Search"
          onChange={(e) => handleQuery(e)}
          value={query()}
        />
        <button type="submit" onClick={(e) => handleSearch(e)}>
          Search
        </button>
        <div class="row">
          {loading() ? (
            <p>Loading...</p>
          ) : users().length > 0 ? (
            <UserDisplay users={users()} />
          ) : (
            <p>No users found</p>
          )}
        </div>
      </form>
    </div>
  );
};

export default Search;
