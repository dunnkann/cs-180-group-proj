import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        // Split sections by semicolon (;) to process main attributes, friends, blocked users, and conversations
        String[] sections = userData.split("; ");
        
        // Parse main attributes
        String[] mainAttributes = sections[0].split(", ");
        if (mainAttributes.length < 5) {
            throw new IllegalArgumentException("Expected format: Username: <username>, Password: <password>, UserID: <userId>, Description: <description>, FriendsOnly: <friendsOnly>");
        }
    
        this.username = mainAttributes[0].split(": ")[1].trim();
        this.password = mainAttributes[1].split(": ")[1].trim();
        try {
            this.userId = Integer.parseInt(mainAttributes[2].split(": ")[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid userId. It must be an integer.");
        }
        this.description = mainAttributes[3].split(": ")[1].trim();
        this.friendsOnly = Boolean.parseBoolean(mainAttributes[4].split(": ")[1].trim());
    
        // Parse friendList from "Friends: [id1, id2, ...]"
        this.friendList = new ArrayList<>();
        if (sections.length > 1 && sections[1].startsWith("Friends: [")) {
            String friendsSection = sections[1].substring(10, sections[1].length() - 1).trim(); // Remove "Friends: [" and ending "]"
            if (!friendsSection.isEmpty()) {
                String[] friendIds = friendsSection.split(",");
                for (String friendId : friendIds) {
                    try {
                        int id = Integer.parseInt(friendId.trim());
                        User friend = new User("Friend", "password", id, ""); // Create a User with the id; other fields are placeholders
                        this.friendList.add(friend);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid friendId. Each friendId must be an integer.");
                    }
                }
            }
        }
    
        // Parse blockList from "Blocked Users: [id1, id2, ...]"
        this.blockList = new ArrayList<>();
        if (sections.length > 2 && sections[2].startsWith("Blocked Users: [")) {
            String blockedSection = sections[2].substring(15, sections[2].length() - 1).trim(); // Remove "Blocked Users: [" and ending "]"
            if (!blockedSection.isEmpty()) {
                String[] blockedIds = blockedSection.split(",");
                for (String blockedId : blockedIds) {
                    try {
                        int id = Integer.parseInt(blockedId.trim());
                        User blockedUser = new User("BlockedUser", "password", id, ""); // Create User with id only
                        this.blockList.add(blockedUser);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid blockedId. Each blockedId must be an integer.");
                    }
                }
            }
        }
    
        // Parse conversations from "Conversations: [conversation1, conversation2, ...]"
        this.conversations = new ArrayList<>();
        if (sections.length > 3 && sections[3].startsWith("Conversations: [")) {
            String conversationsSection = sections[3].substring(15, sections[3].length() - 1).trim(); // Remove "Conversations: [" and ending "]"
            if (!conversationsSection.isEmpty()) {
                String[] convs = conversationsSection.split(",");
                for (String conv : convs) {
                    Conversation conversation = new Conversation(conv.trim()); // Assuming Conversation has a constructor that takes a summary string
                    this.conversations.add(conversation);
                }
            }
        }
    }
    public User(int userId) {
        this.userId = userId;
        this.username = "UserNumber:";
        this.password = "";
        this.description = "";
        this.friendList = Collections.synchronizedList(new ArrayList<>());
        this.blockList = Collections.synchronizedList(new ArrayList<>());
        this.conversations = Collections.synchronizedList(new ArrayList<>());
    }
    public User(String username, String password, int userId, String description) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.description = description;
        this.friendList = Collections.synchronizedList(new ArrayList<>());
        this.blockList = Collections.synchronizedList(new ArrayList<>());
        this.conversations = Collections.synchronizedList(new ArrayList<>());
    }
    
    public User(String username, String password, int userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.friendList = Collections.synchronizedList(new ArrayList<>());
        this.blockList = Collections.synchronizedList(new ArrayList<>());
        this.conversations = Collections.synchronizedList(new ArrayList<>());
    }
    public User(String username, String password, AtomicInteger userId) {
        this.username = username;
        this.password = password;
        this.userId = userId.get();
        this.friendList = Collections.synchronizedList(new ArrayList<>());
        this.blockList = Collections.synchronizedList(new ArrayList<>());
        this.conversations = Collections.synchronizedList(new ArrayList<>());
    }
    
    public User(String username, String password, int userId, String description, 
                List<User> friendList, List<User> blockList, List<Conversation> conversations) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.description = description;
        this.friendList = (friendList != null) ? new ArrayList<>(friendList) : new ArrayList<>();
        this.blockList = (blockList != null) ? new ArrayList<>(blockList) : new ArrayList<>();
        this.conversations = (conversations != null) ? new ArrayList<>(conversations) : new ArrayList<>();
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
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Username: %s, Password: %s, UserID: %d, Description: %s, FriendsOnly: %s",
                                username, password, userId, description != null ? description : "", friendsOnly));
        
        // Format friendList with user IDs
        sb.append("; Friends: [");
        for (User friend : friendList) {
            sb.append(friend.getUserId()).append(",");
        }
        if (!friendList.isEmpty()) sb.setLength(sb.length() - 1); // Remove trailing comma
        sb.append("]");
    
        // Format blockList with user IDs
        sb.append("; Blocked Users: [");
        for (User blocked : blockList) {
            sb.append(blocked.getUserId()).append(",");
        }
        if (!blockList.isEmpty()) sb.setLength(sb.length() - 1); // Remove trailing comma
        sb.append("]");
    
        // Format conversations (assuming each conversation has a `toString` method)
        sb.append("; Conversations: [");
        for (Conversation conv : conversations) {
            sb.append(conv.toString()).append(",");
        }
        if (!conversations.isEmpty()) sb.setLength(sb.length() - 1); // Remove trailing comma
        sb.append("]");
    
        return sb.toString();
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

    public List<User> getFriends() {
        return friendList;
    }

    public List<User> getBlockList() {
        return blockList;
    }
}
