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
    
    public boolean readUserFile(String userFile) {
        try {
            File f = new File(userFile);
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.close();
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }
}

