import java.util.*;

public class User implements UserActions {
    private String username;
    private String password;
    private int userId;
    private String description;
    private boolean friendsOnly = false;
    private List<Conversation> conversations = Collections.synchronizedList(new ArrayList<>());
    private List<User> friendList = Collections.synchronizedList(new ArrayList<>());
    private List<User> blockList = Collections.synchronizedList(new ArrayList<>());

    
    public User(String userData) {
        String[] parts = userData.split(",");
        
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid format. Expected format: username,password,userId[,description]");
        }
        
        this.username = parts[0].trim();
        this.password = parts[1].trim();
        
        try {
            this.userId = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid userId. It must be an integer.");
        }
        
        if (parts.length > 3) {
            this.description = parts[3].trim();
        }
    }

    public void addFriend(User friend) {
        friendList.add(friend);
    }

    public void removeFriend(User friend) {
        friendList.remove(friend);
    }

    public boolean blockUser(User u) {
        if (isUserBlocked(u)) {
            return false;
        }
        blockList.add(u);
        return true;
    }

    public boolean unblockUser(User u) {
        return blockList.remove(u);
    }

    public boolean isUserBlocked(User u) {
        return blockList.contains(u);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        User user = (User) o;
        return userId == user.userId && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%s", username, password, userId, description != null ? description : "");
    }

        // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for friendsOnly
    public boolean isFriendsOnly() {
        return friendsOnly;
    }

    public void setFriendsOnly(boolean friendsOnly) {
        this.friendsOnly = friendsOnly;
    }


}
