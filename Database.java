import java.util.*;
import java.io.*;
public class Database {
    ArrayList<User> users;
    String userFile;
    String conversationFile;

    public Database(String users, String conversations) {
        userFile = users;
        conversationFile = conversations;
    }
    
    public boolean readUserFile(String) {
        try {
            File f = new File()
            BufferedReader br = new BufferedReader(FileReader(f));
            
        } catch (IOException e) {

        }
    }
}

