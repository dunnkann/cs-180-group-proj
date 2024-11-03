import java.util.*;
public class User implements UserActions {
    private String username;
    private String password;
    private int userId;
    private String description;
    private boolean friendsOnly = false;
    private ArrayList<Conversation> conversations = new ArrayList<>();
    private ArrayList<User> friendList = new ArrayList<>();
    private ArrayList<User> blockList = new ArrayList<>();
    
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
        
        // Optional field for user description, if provided
        if (parts.length > 3) {
            // Assume you may want to add a description field to the User class if not already present
            this.description = parts[3].trim();
        }
    }

    public void addFriend(User friend) {
        for (User f : this.friendList) {
            if (friend.equals(f)) {
                //Error Message
                return;
            }
        }
        friendList.add(friend);
    }
    public void removeFriend(User friend) {
        for (User f : this.friendList) {
            if (friend.equals(f)) {
                //Error Message
                friendList.remove(friend);
            }
        }
        
    }
    public void sendMessage(Conversation c, Message message) {
        //TODO
    }
    public void sendMessage(User u, Message message) {
        if (u.friendsOnly && (this.isFriend(u)) && (this.isBlocked(u) !=)) {
            //TODO
        }
    }
    public void deleteMessage(Conversation c, int messageId) {
        //TODO
    }
    public boolean blockUser(User u) {
        if (this.isUserBlocked(u)) {
            //error message
            return false;
        }
        this.blockList.add(u);
    }
    public boolean unblockUser(User u) {
        if (this.isUserBlocked(u)) {
            this.blockList.remove(u);
        }
        return false;
    }

    public boolean isUserBlocked(User u) {
        for (User f : this.blockList) {
            if (u.equals(f)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        // Check if the object is null or not of the same type
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        // Cast to User and compare the unique fields
        User user = (User) o;
        return userId == user.userId && Objects.equals(username, user.username);
    }

    
    @Override
    public String toString() {
        return String.format("%s,%s,%d,%s", username, password, userId, description != null ? description : "");
    }
<<<<<<< HEAD

=======
    
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

>>>>>>> 60de455262ab02334289959cf60922176b467e3f
}
