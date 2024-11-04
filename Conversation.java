import java.util.*;
import java.io.*;
import javax.swing.*;

public class Conversation{
    private User user1;
    private User user2;
    private Message messages;

    public Conversate(String conversationString){

        String[] parts = conversationString.split("   ");
        
        this.user1 = new User(parts[0].trim());
        this.user2 = new User(parts[1].trim());
        String user1message = parts[2].trim();
        String user2message = parts[3].trim();

        if(user1message.isEmpty()){
            messages.add(new message1(user1, user2, user1message));
        }

        if(user2message.isEmpty()){
            messages.add(new message1(user2, user1, user2message));
        }



    }

    public void displayMessages(){
        System.out.println(user1.getUsername() + " : " + user1message + "      " + user2.getUsername() + " : " + user2message);
    }



}