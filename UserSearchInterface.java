import java.io.IOException;

public interface UserSearchInterface {
    /**
     * Searches for a user by their name.
     *
     * @param search the username to search for
     * @return the result of the search as a String message
     */
    String check(String search) throws IOException;
}
