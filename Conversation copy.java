import java.util.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;

public class Conversation {
    private int conversationID;
    private User user1;
    private User user2;
    private ArrayList<Message> messages; // Stores the messages

    // Constructor
    public Conversation(String conversationString) {

        String[] parts = conversationString.split(",");

        String[] user1Parts = parts[0].trim().split(":");
        String[] user2Parts = parts[1].trim().split(":");

        // Create User objects like Name & UserID and seperates them with commas
        this.user1 = new User(user1Parts[0].trim(), Integer.parseInt(user1Parts[1].trim()));
        this.user2 = new User(user2Parts[0].trim(), Integer.parseInt(user2Parts[1].trim()));
        this.messages = new ArrayList<>();
        this.conversationID = DatabaseID();
    }

    // Adds a message to the conversation
    public void addMessage(User sender, String text) {
        User receiver;
        if (sender.equals(user1)) {
            receiver = user2; 
        } else {
            receiver = user1; 
        }
        
        // Creates a message object and adds it to the list
        Message message = new Message(sender, receiver, text);
        messages.add(message);

        message.printMessage();
    }

    // Gets all the messages
    public ArrayList<Message> getMessages() {
        return messages; // Returns the list of messages
    }


    private int DatabaseID() {
        int uniqueId = -1;

        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/cool_db"; 
        String user = "postgres";
        String password = "12345";

        // Connecting to database to grab an ID
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = "SELECT nextval('conversation_id_seq')"; // Change this to match your database
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                uniqueId = resultSet.getInt(1);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uniqueId; // Returns the ID
    }

    // Makes the conversation into a string
    public String makeString() {
        StringBuilder sb = new StringBuilder();
        sb.append(user1.getName()).append(" (ID: ").append(user1.getUserId()).append("), ")
          .append(user2.getName()).append(" (ID: ").append(user2.getUserId()).append(")\n");

        for (Message message : messages) {
            sb.append(message.getSender().getName())
              .append(" (ID: ").append(message.getSender().getUserId()).append("): ")
              .append(message.getText()).append("\n");
        }
        
        return sb.toString();
    }
}