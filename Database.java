import java.util.*;
import java.io.*;
public class Database {
    private ArrayList<User> users;
    private ArrayList<Conversation> conversations;
    private String userFile;
    private String conversationFile;

    public Database(String users, String conversations) {
        userFile = users;
        conversationFile = conversations;
    }
    
    public boolean readUserFile() {
        try {
            File f = new File(userFile);
            BufferedReader br = new BufferedReader(new FileReader(f));
            while(true) {
               String line = br.readLine();
               if (line == null)
                    break;
                User user = new User(line);
                users.add(user);
            }
            br.close();
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }

    public boolean readConversationFile() {
        try {
            File f = new File(conversationFile);
            BufferedReader br = new BufferedReader(new FileReader(f));
            while(true) {
               String line = br.readLine();
               if (line == null)
                    break;
                Conversation conversation = new conversation(line);
                conversations.add(conversation);
            }
            br.close();
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }

    public User searchUsers(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername() == username) {
                return users.get(i);
            }
        }
        return null;
    }

    public boolean removeFriendData(String username, String friendName) {
        User user = this.searchUsers(username);
        User friend = this.searchUsers(friendName);
        boolean isFriend = false;
        for (int i = 0; i < user.getFriends().size(); i++) {
            if (user.getFriends().get(i).equals(friend)) {
                isFriend = true;
                break;
            }
        }
        if (user == null || friend == null || !isFriend) {
            return false;
        } else {
            user.removeFriend(friend);
            return true;
        }
        
    }

    public boolean addFriendData(String username, String friendName) {
        User user = this.searchUsers(username);
        User friend = this.searchUsers(friendName);
        for (int i = 0; i < user.getFriends().size(); i++) {
            if (user.getFriends().get(i).equals(friend)) {
                return false;
            }
        }
        if (user == null || friend == null) {
            return false;
        } else {
            user.addFriend(friend);
            return true;
        }
        
    }

    public boolean blockFriendData(String username, String friendName) {
        User user = this.searchUsers(username);
        User friend = this.searchUsers(friendName);
        boolean isFriend = false;
        for (int i = 0; i < user.getFriends().size(); i++) {
            if (user.getFriends().get(i).equals(friend)) {
                isFriend = true;
                break;
            }
        }
        if (!isFriend)
            return false;
        for (int i = 0; i < user.getBlockList().size(); i++) {
            if (user.getBlockList().get(i).equals(friend)) {
                return false;
            }
        }

        user.blockUser(friend);
        return true;

    }

    public boolean unblockFriendData(String username, String friendName) {
        User user = this.searchUsers(username);
        User friend = this.searchUsers(friendName);
        boolean isFriend = false;
        boolean isBlocked = false;
        for (int i = 0; i < user.getFriends().size(); i++) {
            if (user.getFriends().get(i).equals(friend)) {
                isFriend = true;
                break;
            }
        }
        if (!isFriend)
            return false;
        for (int i = 0; i < user.getBlockList().size(); i++) {
            if (user.getBlockList().get(i).equals(friend)) {
                isBlocked = true;
            }
        }
        if (!isBlocked)
            return false;
        user.unblockUser(friend);
        return true;

    }
}

