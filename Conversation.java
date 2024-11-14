import java.util.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;

public class Conversation {
    private User user1;
    private User user2;
    private ArrayList<Message> messages; // Stores the messages

    // Constructor
    public Conversation(String conversationString) {
        String[] parts = conversationString.split(",");

        String[] user1Parts = parts[0].trim().split(":");
        String[] user2Parts = parts[1].trim().split(":");

        // Create User objects like Nmae & UserID and seperates them with commas
        this.user1 = new User(user1Parts[0].trim(), Integer.parseInt(user1Parts[1].trim()));
        this.user2 = new User(user2Parts[0].trim(), Integer.parseInt(user2Parts[1].trim()));
        this.messages = new ArrayList<>();
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
    }

    // Gets all the messages
    public ArrayList<Message> getMessages() {
        return messages; // Returns the list of messages
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
