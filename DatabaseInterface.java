import java.util.ArrayList;

public interface DatabaseInterface {
    boolean readUserFile();
    boolean readConversationFile();
    // Uncomment if profile handling is required
    // boolean readProfileFile();
    User searchUsers(String username);
    User searchUsers(int userId);
    boolean removeFriendData(String username, int friendId);
    boolean addFriendData(String username, int friendId);
    boolean blockFriendData(String username, int friendId);
    boolean unblockFriendData(String username, String friendName);
    boolean writeOutput();
}
