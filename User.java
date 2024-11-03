import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements UserActions, ProfileManager {
    private String username;
    private String password;
    private AtomicInteger userId; // Change to AtomicInteger
    private boolean friendsOnly = false;
    private ArrayList<Conversation> conversations = new ArrayList<>();
    private ArrayList<User> friendList = new ArrayList<>();
    private ArrayList<User> blockList = new ArrayList<>();
    
    public User(String username, String password, AtomicInteger userId) {
        this.username = username;
        this.password = password;
        this.userId = userId; // Accept AtomicInteger
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId.get(); // Use get() to retrieve the int value
    }

    public boolean isFriendsOnly() {
        return friendsOnly;
    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    public ArrayList<User> getFriendList() {
        return friendList;
    }

    public ArrayList<User> getBlockList() {
        return blockList;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(AtomicInteger userId) {
        this.userId = userId; // Set AtomicInteger
    }

    public void setFriendsOnly(boolean friendsOnly) {
        this.friendsOnly = friendsOnly;
    }

    public void addFriend(User friend) {
        for (User f : this.friendList) {
            if (friend.equals(f)) {
                // Error Message
                return;
            }
        }
        friendList.add(friend);
    }

    public void removeFriend(User friend) {
        for (User f : this.friendList) {
            if (friend.equals(f)) {
                // Error Message
                friendList.remove(friend);
                return; // Break out of the method after removing the friend
            }
        }
    }

    public void sendMessage(Conversation c, Message message) {
        // TODO
    }

    public void sendMessage(User u, Message message) {
        if (u.isFriendsOnly() && this.isFriend(u) && !this.isBlocked(u)) {
            // TODO
        }
    }

    public void deleteMessage(Conversation c, int messageId) {
        // TODO
    }

    public boolean blockUser(User u) {
        // TODO
    }

    public boolean unblockUser(User u) {
        // TODO
    }

    public boolean isUserBlocked(User u) {
        for (User f : this.blockList) {
            if (u.equals(f)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFriend(User u) {
        return friendList.contains(u);
    }

    public boolean equals(Object o) {
        // Implement equals method
    }
}
