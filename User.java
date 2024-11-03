import java.util.*;
public class User implements UserActions, ProfileManager {
    private String username;
    private String password;
    private int userId;
    private boolean friendsOnly = false;
    private ArrayList<Conversation> conversations = new ArrayList<>();
    private ArrayList<User> friendList = new ArrayList<>();
    private ArrayList<User> blockList = new ArrayList<>();
    
    public User(String username, String password, int userId) {
        //TODO: take one string instead and parse it to turn into username and password
        this.username = username;
        this.password = password;
        this.userId = userId;
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
        //TODO
    }
    public boolean unblockUser(User u) {
        
    }

    public boolean isBlocked(User u) {
        for (User f : this.blockList) {
            if (u.equals(f)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {

    }



}
