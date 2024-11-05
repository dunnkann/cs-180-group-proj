import java.io.*;
import java.util.*;


public class Message {
    private User sender;
    private User receiver;
    private String text;

    
    public Message(User sender, User receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    // Getters for sender, reciever, and text
    public User getSender() {
        return sender;
    }

    // Getter for receiver
    public User getReceiver() {
        return receiver;
    }

    // Getter for message text
    public String getText() {
        return text;
    }

    // Method to display the message
    public void printMessage() {
        System.out.println(sender.getName() + " (ID: " + sender.getUserId() + "): " + text);
    }
}
