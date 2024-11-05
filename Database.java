import java.util.*;
import java.io.*;

public class Database {
    private ArrayList<User> users;
    private ArrayList<Conversation> conversations;
    private String userFile;
    private String conversationFile;

    public Database(String users, String conversations) {
        this.userFile = users;
        this.conversationFile = conversations;
        this.users = new ArrayList<>();
        this.conversations = new ArrayList<>();
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

        if (user == null || friend == null || !user.getFriends().contains(friend)) {
            return false;
        }

        user.removeFriend(friend);
        return true;
    }

    public boolean addFriendData(String username, int friendId) {
        User user = searchUsers(username);
        User friend = searchUsers(friendId);

        if (user == null || friend == null || user.getFriends().contains(friend)) {
            return false;
        }

        user.addFriend(friend);
        return true;
    }

    public boolean blockFriendData(String username, int friendId) {
        User user = searchUsers(username);
        User friend = searchUsers(friendId);

        if (user == null || friend == null || !user.getFriends().contains(friend) || user.getBlockList().contains(friend)) {
            return false;
        }

        user.blockUser(friend);
        return true;
    }

    public boolean unblockFriendData(String username, String friendName) {
        User user = searchUsers(username);
        User friend = searchUsers(friendName);

        if (user == null || friend == null || !user.getFriends().contains(friend) || !user.getBlockList().contains(friend)) {
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
}
