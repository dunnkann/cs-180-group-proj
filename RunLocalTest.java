import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
/**
 * A framework to run public test cases for Phase 1.
 *
 * 
 *
 * @author Duncan,Marveon,Kiefer,Victor
 * @version Nov 4, 2024
 */
public class RunLocalTest {
    public class TestCase {
      
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

}
