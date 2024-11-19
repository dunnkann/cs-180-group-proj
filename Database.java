import java.util.*;
import java.io.*;

public class Database implements DatabaseInterface {
    private List<User> users = Collections.synchronizedList(new ArrayList<>());
    private List<Conversation> conversations = Collections.synchronizedList(new ArrayList<>());
    // private ArrayList<UserProfile> profiles;  // Uncomment if UserProfile class is implemented
    private String userFile;
    private String conversationFile;
    // private String profileFile;  // Uncomment if profile data is required

    public Database(String users, String conversations) {
        this.userFile = users;
        this.conversationFile = conversations;
        this.users = new ArrayList<>();
        this.conversations = new ArrayList<>();
        // this.profiles = new ArrayList<>();  // Uncomment to initialize profiles list
    }

    public boolean readUserFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                User user = new User(line);
                users.add(user);
            }
            return !users.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean assignFriends() {
        boolean fail = false;
        for (int i = 0; i < users.size(); i++) {
            
            User current = users.get(i);
            List<User> friends = current.getFriendList();
            //look through the current user's friend list, find each friend, initialize them properly, or increase notFound
            for (int j = 0; j < current.getFriendList().size(); j++) {
                int friendId = friends.get(i).getUserId();
                User friend = this.searchUsers(friendId);
                if (friend == null)
                    fail = true;
                else
                    friends.set(i, friend);
            }
        }
        if (fail)
            return false;
        else
            return true;
    }

    public boolean assignBlocked() {
        boolean fail = false;
        for (int i = 0; i < users.size(); i++) {
            
            User current = users.get(i);
            List<User> blockedList = current.getBlockList();
            //look through the current user's friend list, find each friend, initialize them properly, or increase notFound
            for (int j = 0; j < blockedList.size(); j++) {
                int blockedId = blockedList.get(i).getUserId();
                User blocked = this.searchUsers(blockedId);
                if (blocked == null)
                    fail = true;
                else
                    blockedList.set(i, blocked);
            }
        }
        if (fail)
            return false;
        else
            return true;
    }

    public boolean readConversationFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(conversationFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Conversation conversation = new Conversation(line);
                conversations.add(conversation);
            }
            return !conversations.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Uncomment this method if profile file reading is required
    /*
    public boolean readProfileFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(profileFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                UserProfile profile = new UserProfile(line);
                profiles.add(profile);
            }
            return !profiles.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    */

    public User searchUsers(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User searchUsers(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public boolean removeFriendData(String username, int friendId) {
        User user = searchUsers(username);
        User friend = searchUsers(friendId);

        if (user == null || friend == null || !user.getFriendList().contains(friend)) {
            return false;
        }

        user.removeFriend(friend);
        return true;
    }

    public boolean addFriendData(String username, int friendId) {
        User user = searchUsers(username);
        User friend = searchUsers(friendId);

        if (user == null || friend == null || user.getFriendList().contains(friend)) {
            return false;
        }

        user.addFriend(friend);
        return true;
    }

    public boolean blockFriendData(String username, int friendId) {
        User user = searchUsers(username);
        User friend = searchUsers(friendId);

        if (user == null || friend == null || !user.getFriendList().contains(friend) || user.getBlockList().contains(friend)) {
            return false;
        }

        user.blockUser(friend);
        return true;
    }

    public boolean unblockFriendData(String username, String friendName) {
        User user = searchUsers(username);
        User friend = searchUsers(friendName);

        if (user == null || friend == null || !user.getFriendList().contains(friend) || !user.getBlockList().contains(friend)) {
            return false;
        }

        user.unblockUser(friend);
        return true;
    }

    public boolean writeOutput() {
        try (BufferedWriter bwU = new BufferedWriter(new FileWriter(userFile));
             BufferedWriter bwC = new BufferedWriter(new FileWriter(conversationFile))) {

            for (int i = 0; i < users.size(); i++) {
                bwU.write(users.get(i).toString());
                if (i < users.size() - 1) bwU.newLine();
            }
                bwU.close();

            
            

            //Writer for conversation
            File c = new File(conversationFile);

            // go through entire conversation arraylist and print each conversation to a new line
            for (int j = 0; j < conversations.size(); j++) {
                bwC.write(conversations.get(j).toString());
                if (j < conversations.size() - 1) bwC.newLine();
            }

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
    }
    
///////
    // public static void main(String[] args) {

    // }
}
