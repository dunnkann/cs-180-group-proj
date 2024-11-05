import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
/**
 * Test cases for all the class tests.
 *
 * These unit tests were designed to validate the functionality of all classes used in this project.
 *
 * This code was created by ChatGpt on 11/4/2024.
 *
 * Based on general best practices for unit testing with JUnit and Mockito.
 * - JUnit 5 Documentation: https://junit.org/junit5/
 * - Mockito Documentation: https://site.mockito.org/
 *
 * Test scenarios follow standard conventions for mocking dependencies, testing I/O operations, and ensuring proper functionality of methods.
 */
public class RunLocalTest {
    public class TestCase {
      
    }
    import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import java.io.*;

class DatabaseTest {

    private Database db;
    private User user1, user2;
    
    @BeforeEach
    void setUp() {
        db = new Database("users.txt", "conversations.txt");
        user1 = mock(User.class);
        user2 = mock(User.class);
    }

    @Test
    void testReadUserFile_Success() throws IOException {
        // Simulate reading a file
        File file = mock(File.class);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn("user1", "user2", null);  // simulate two users in the file

        db.readUserFile();
        
        // Verify if the users are added to the database
        Assertions.assertTrue(db.getUsers().contains(user1));
        Assertions.assertTrue(db.getUsers().contains(user2));
    }
    
    @Test
    void testReadUserFile_Failure() throws IOException {
        File file = mock(File.class);
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenThrow(new IOException("File not found"));

        boolean result = db.readUserFile();
        
        // Assert false if the method catches the exception
        Assertions.assertFalse(result);
    }

        @Test
    void testAddFriendData_Success() {
        // Mock User objects
        User user = mock(User.class);
        User friend = mock(User.class);
        
        when(db.searchUsers("user1")).thenReturn(user);
        when(db.searchUsers(2)).thenReturn(friend);
        
        // Simulate adding the friend
        when(user.getFriends()).thenReturn(new ArrayList<>());
        
        boolean result = db.addFriendData("user1", 2);
        
        // Assert true because the friend should be added
        Assertions.assertTrue(result);
    }
        
    @Test
    void testAddFriendData_UserNotFound() {
        when(db.searchUsers("user1")).thenReturn(null);
        
        boolean result = db.addFriendData("user1", 2);
        
        Assertions.assertFalse(result); // False because user1 does not exist
    }

    @Test
void testRemoveFriendData_Success() {
    User user = mock(User.class);
    User friend = mock(User.class);

    when(db.searchUsers("user1")).thenReturn(user);
    when(db.searchUsers(2)).thenReturn(friend);
    when(user.getFriends()).thenReturn(new ArrayList<>(List.of(friend)));
    
    // Simulate removing a friend
    boolean result = db.removeFriendData("user1", 2);

    Assertions.assertTrue(result); // Assert the friend was removed
    }

    @Test
    void testRemoveFriendData_FriendNotFound() {
        User user = mock(User.class);
        when(db.searchUsers("user1")).thenReturn(user);
        when(db.searchUsers(2)).thenReturn(null);
        
        boolean result = db.removeFriendData("user1", 2);
        
        Assertions.assertFalse(result); // Assert failure because friend does not exist
    }
    @Test
    void testBlockFriendData_Success() {
        User user = mock(User.class);
        User friend = mock(User.class);
        
        when(db.searchUsers("user1")).thenReturn(user);
        when(db.searchUsers(2)).thenReturn(friend);
        
        when(user.getFriends()).thenReturn(new ArrayList<>(List.of(friend)));
        when(user.getBlockList()).thenReturn(new ArrayList<>());
        
        boolean result = db.blockFriendData("user1", 2);
        
        Assertions.assertTrue(result); // Assert the user was blocked
    }
        
    @Test
    void testBlockFriendData_NotAFriend() {
        User user = mock(User.class);
        User friend = mock(User.class);
        
        when(db.searchUsers("user1")).thenReturn(user);
        when(db.searchUsers(2)).thenReturn(friend);
        
        when(user.getFriends()).thenReturn(new ArrayList<>());  // Not a friend
        
        boolean result = db.blockFriendData("user1", 2);
        
        Assertions.assertFalse(result); // Assert false because they aren't friends
    }
    
    @Test
    void testWriteOutput_Success() throws IOException {
        // Setup mock file writer
        File userFile = mock(File.class);
        File conversationFile = mock(File.class);
        BufferedWriter writer = mock(BufferedWriter.class);
        
        when(writer.write(anyString())).thenReturn(null);
        when(writer.newLine()).thenReturn(null);
        
        boolean result = db.writeOutput();  // Assuming we have data added to db
        
        Assertions.assertTrue(result); // If no exception is thrown, the result is true
    }
    
    @Test
    void testWriteOutput_Failure() throws IOException {
        // Setup a failing case (e.g., file not found)
        when(db.writeOutput()).thenThrow(new IOException("Unable to write to file"));
        
        boolean result = db.writeOutput();
        
        Assertions.assertFalse(result); // If an exception is thrown, the result is false
    }
    
    @Test
    void testAssignFriends_Success() {
        User user = mock(User.class);
        User friend = mock(User.class);
        
        when(db.searchUsers(2)).thenReturn(friend);
        when(user.getFriends()).thenReturn(new ArrayList<>(List.of(friend)));
        
        boolean result = db.assignFriends();
        
        Assertions.assertTrue(result); // Assert that friends were assigned
    }
    
    @Test
    void testAssignFriends_Failure() {
        User user = mock(User.class);
        
        when(user.getFriends()).thenReturn(new ArrayList<>());
        
        boolean result = db.assignFriends();
        
        Assertions.assertFalse(result); // Assert that failure occurs when no friends found
    }

}
public class UserTest {

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        user1 = new User("Alice", "password123", 1, "Alice's profile");
        user2 = new User("Bob", "password456", 2, "Bob's profile");
        user3 = new User("Charlie", "password789", 3, "Charlie's profile");
    }

    @Test
    public void testUserCreation() {
        assertEquals("Alice", user1.getUsername());
        assertEquals("password123", user1.getPassword());
        assertEquals(1, user1.getUserId());
        assertEquals("Alice's profile", user1.getDescription());
    }

    @Test
    public void testAddFriend() {
        user1.addFriend(user2);
        assertTrue(user1.getFriendList().contains(user2));
    }

    @Test
    public void testRemoveFriend() {
        user1.addFriend(user2);
        user1.removeFriend(user2);
        assertFalse(user1.getFriendList().contains(user2));
    }

    @Test
    public void testBlockUser() {
        assertTrue(user1.blockUser(user2));
        assertTrue(user1.isUserBlocked(user2));
        assertFalse(user1.blockUser(user2)); // Blocking again should return false
    }

    @Test
    public void testUnblockUser() {
        user1.blockUser(user2);
        assertTrue(user1.unblockUser(user2));
        assertFalse(user1.isUserBlocked(user2));
    }

    @Test
    public void testIsUserBlocked() {
        user1.blockUser(user3);
        assertTrue(user1.isUserBlocked(user3));
        assertFalse(user1.isUserBlocked(user2));
    }

    @Test
    public void testEqualsAndHashCode() {
        User userDuplicate = new User("Alice", "password123", 1, "Alice's profile");
        assertEquals(user1, userDuplicate);
        assertEquals(user1.hashCode(), userDuplicate.hashCode());
        assertNotEquals(user1, user2); // Different userId and username
    }

    @Test
    public void testToStringFormat() {
        user1.addFriend(user2);
        user1.blockUser(user3);
        
        String expectedOutput = "Username: Alice, Password: password123, UserID: 1, Description: Alice's profile, FriendsOnly: false; " +
                                "Friends: [2]; Blocked Users: [3]; Conversations: []";
        assertEquals(expectedOutput, user1.toString());
    }

    @Test
    public void testUserFromStringConstructor() {
        String userData = "Username: Dave, Password: pass, UserID: 4, Description: Dave's profile, FriendsOnly: false; " +
                          "Friends: [1, 2]; Blocked Users: [3]; Conversations: []";
        User user = new User(userData);

        assertEquals("Dave", user.getUsername());
        assertEquals("pass", user.getPassword());
        assertEquals(4, user.getUserId());
        assertEquals("Dave's profile", user.getDescription());
        assertEquals(2, user.getFriendList().size());
        assertEquals(1, user.getBlockList().size());
    }

    @Test
    public void testUserIdConstructor() {
        User dummyUser = new User(100);
        assertEquals(100, dummyUser.getUserId());
        assertEquals("UserNumber:", dummyUser.getUsername());
        assertEquals("", dummyUser.getPassword());
        assertEquals("", dummyUser.getDescription());
    }

    @Test
    public void testAtomicIntegerConstructor() {
        AtomicInteger userId = new AtomicInteger(5);
        User user = new User("Eve", "securePass", userId);
        assertEquals("Eve", user.getUsername());
        assertEquals("securePass", user.getPassword());
        assertEquals(5, user.getUserId());
    }
}

 public class MessageTest {
    
    // Test the creation of a Message object
    @Test
    public void testMessageCreation() {
        // Create User objects
        User sender = new User("Alice", 1);
        User receiver = new User("Bob", 2);
        String messageText = "Hello, Bob!";
        
        // Create Message object
        Message message = new Message(sender, receiver, messageText);
        
        // Check if the sender, receiver, and text are correct
        assertEquals("Sender should be Alice", sender, message.getSender());
        assertEquals("Receiver should be Bob", receiver, message.getReceiver());
        assertEquals("Message text should match", messageText, message.getText());
    }
    
    // Test the printMessage method
    @Test
    public void testPrintMessage() {
        User sender = new User("Alice", 1);
        User receiver = new User("Bob", 2);
        String messageText = "Hello, Bob!";
        
        Message message = new Message(sender, receiver, messageText);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        message.printMessage();
        System.setOut(System.out);
        String expectedOutput = "Alice (ID: 1): Hello, Bob!";
        
        assertEquals("Output should match expected message format", expectedOutput, outputStream.toString().trim());
    }
}

    public class ConversationTest {
    
    // Test the creation of a Conversation object
    @Test
    public void testConversationCreation() {
        // Create a conversation string with user names and IDs
        String conversationString = "Alice:1, Bob:2";
        
        // Create a Conversation object
        Conversation conversation = new Conversation(conversationString);
        
        // Check that user1 and user2 are created correctly
        assertEquals("User1's name should be Alice", "Alice", conversation.getUser1().getName());
        assertEquals("User1's ID should be 1", 1, conversation.getUser1().getUserId());
        assertEquals("User2's name should be Bob", "Bob", conversation.getUser2().getName());
        assertEquals("User2's ID should be 2", 2, conversation.getUser2().getUserId());
        
        // Check that the messages list is empty at creation
        assertTrue("Messages list should be empty", conversation.getMessages().isEmpty());
    }

    // Test adding messages to the conversation
    @Test
    public void testAddMessage() {
        String conversationString = "Alice:1, Bob:2";
        Conversation conversation = new Conversation(conversationString);
        
        // Add a message from Alice to Bob
        conversation.addMessage(conversation.getUser1(), "Hello, Bob!");
        
        // Check that the message was added
        ArrayList<Message> messages = conversation.getMessages();
        assertEquals("There should be 1 message in the conversation", 1, messages.size());
        assertEquals("Message text should match", "Hello, Bob!", messages.get(0).getText());
        assertEquals("Sender should be Alice", "Alice", messages.get(0).getSender().getName());
        assertEquals("Receiver should be Bob", "Bob", messages.get(0).getReceiver().getName());
    }

    // Test the string representation of the conversation
    @Test
    public void testMakeString() {
        String conversationString = "Alice:1, Bob:2";
        Conversation conversation = new Conversation(conversationString);
        
        conversation.addMessage(conversation.getUser1(), "Hello, Bob!");
        conversation.addMessage(conversation.getUser2(), "Hi, Alice!");
        
        String expectedOutput = "Alice (ID: 1), Bob (ID: 2)\n"
                              + "Alice (ID: 1): Hello, Bob!\n"
                              + "Bob (ID: 2): Hi, Alice!\n";

       
        assertEquals("String representation should match", expectedOutput, conversation.makeString());
    }
}

}
