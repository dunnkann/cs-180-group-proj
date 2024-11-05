import java.util.*;
import java.io.*;
public class Database {
    private List<User> users = Collections.synchronizedList(new ArrayList<>());
    private List<Conversation> conversations = Collections.synchronizedList(new ArrayList<>());
    //private ArrayList<UserProfile> profiles;
    private String userFile;
    private String conversationFile;
    //private String profileFile;

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

    public boolean assignFriends() {
        boolean fail = false;
        for (int i = 0; i < users.size(); i++) {
            
            User current = users.get(i);
            List<User> friends = current.getFriends();
            //look through the current user's friend list, find each friend, initialize them properly, or increase notFound
            for (int j = 0; j < current.getFriends().size(); j++) {
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
        try {
            File f = new File(conversationFile);
            BufferedReader br = new BufferedReader(new FileReader(f));
            while(true) {
               String line = br.readLine();
               if (line == null)
                    break;
                Conversation conversation = new Conversation(line);
                conversations.add(conversation);
            }
            br.close();
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }

    // looks through all users and returns user with username(more for when user is searching for someone)
    public User searchUsers(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername() == username) {
                return users.get(i);
            }
        }
        return null;
    }
    // looks through all users and returns user with specified userId (mostly use to identify friends)
    public User searchUsers(int userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == userId) {
                return users.get(i);
            }
        }
        return null;
    }

    public boolean removeFriendData(String username, int friendId) {
        User user = this.searchUsers(username);
        User friend = this.searchUsers(friendId);
        boolean isFriend = false;

        if (user == null || friend == null) {
            return false;
        }

        for (int i = 0; i < user.getFriends().size(); i++) {
            if (user.getFriends().get(i).equals(friend)) {
                isFriend = true;
                break;
            }
        }

        if (!isFriend)
            return false;
        
        user.removeFriend(friend);
        return true;
        
        
    }

    public boolean addFriendData(String username, int friendId) {
        User user = this.searchUsers(username);
        User friend = this.searchUsers(friendId);

        if (user == null || friend == null) {
            return false;
        }

        for (int i = 0; i < user.getFriends().size(); i++) {
            if (user.getFriends().get(i).equals(friend)) {
                return false;
            }
        }
        
        user.addFriend(friend);
        return true;
        
    }

    public boolean blockFriendData(String username, int friendId) {
        User user = this.searchUsers(username);
        User friend = this.searchUsers(friendId);
        boolean isFriend = false;

        if (user == null || friend == null)
            return false;

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

        if (user == null || friend == null)
            return false;

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

    // public boolean readProfileFile() throws IOException {
    //     try {
    //         File f = new File(profileFile);
    //         BufferedReader br = new BufferedReader(new FileReader(f));
    //         while (true) {
    //             String line = br.readLine();
    //             if (line == null)
    //                 break;
                
    //         }
    //         br.close();
    //         return true;
    //     } catch (IOException e) {
    //         return false;
    //     }
    //  }

    public boolean writeOutput() {
        try {
            //Writer for user
            File u = new File(userFile);
            BufferedWriter bwU = new BufferedWriter(new FileWriter(u));
        
            // go through entire user arraylist and print each user to a new line
            for (int i = 0; i < users.size(); i++) {
                if (i == users.size() - 1)
                    bwU.write(users.get(i).toString());
                else {
                    bwU.write(users.get(i).toString());
                    bwU.newLine();
                }

            }

            bwU.close();
            

            //Writer for conversation
            File c = new File(conversationFile);
            BufferedWriter bwC = new BufferedWriter(new FileWriter(c));

            // go through entire conversation arraylist and print each conversation to a new line
            for (int j = 0; j < conversations.size(); j++) {
                if (j == conversations.size() - 1)
                    bwC.write(conversations.get(j).toString());
                else {
                    bwC.write(conversations.get(j).toString());
                    bwC.newLine();
                }

            }
            bwC.close();
            return true;
        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        
    }
    
///////
    // public static void main(String[] args) {

    // }
}

