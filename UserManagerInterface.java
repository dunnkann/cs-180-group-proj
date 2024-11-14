import java.io.IOException;

public interface UserManagerInterface {
    
    /**
     * Registers a new user with the specified username and password.
     */
    boolean registerUser(String username, String password) throws IOException;

    /**
     * Checks if a username already exists.
     */
    boolean usernameExists(String username) throws IOException;

    /**
     * Logs in a user by verifying the username and password.
     */
    boolean loginUser(String username, String password) throws IOException;

    /**
     * Returns the total number of registered users.
     */
    int getTotalUsers() throws IOException;
}
