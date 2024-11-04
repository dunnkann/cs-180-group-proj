import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class UserTest {
    private User user;
    private User friend1;
    private User friend2;
    private User blockedUser;

    @BeforeEach
    void setUp() {
        // Initialize Users for testing
        friend1 = new User("Username: friend1, Password: pwd1, UserID: 2, Description: Friend User, FriendsOnly: false; Friends: []; Blocked Users: []; Conversations: []");
        friend2 = new User("Username: friend2, Password: pwd2, UserID: 3, Description: Another Friend, FriendsOnly: false; Friends: []; Blocked Users: []; Conversations: []");
        blockedUser = new User("Username: blockedUser, Password: pwd3, UserID: 4, Description: Blocked User, FriendsOnly: false; Friends: []; Blocked Users: []; Conversations: []");

        // Create a test user with initial friends and blocked users
        List<User> initialFriends = new ArrayList<>();
        initialFriends.add(friend1);
        List<User> initialBlocked = new ArrayList<>();
        initialBlocked.add(blockedUser);

        user = new User("testUser", "testPass", 1, "Test Description", initialFriends, initialBlocked, new ArrayList<>());
    }

    @Test
    void testUserCreationFromString() {
        String userData = "Username: newUser, Password: newPass, UserID: 5, Description: New User, FriendsOnly: true; Friends: [friend1]; Blocked Users: [blockedUser]; Conversations: []";
        User newUser = new User(userData);

        assertEquals("newUser", newUser.getUsername());
        assertEquals("newPass", newUser.getPassword());
        assertEquals(5, newUser.getUserId());
        assertEquals("New User", newUser.getDescription());
        assertTrue(newUser.isFriendsOnly());
        assertEquals(1, newUser.getFriendList().size());
        assertEquals(1, newUser.getBlockList().size());
    }

    @Test
    void testAddFriend() {
        user.addFriend(friend2);
        assertTrue(user.getFriendList().contains(friend2));
    }

    @Test
    void testRemoveFriend() {
        user.removeFriend(friend1);
        assertFalse(user.getFriendList().contains(friend1));
    }

    @Test
    void testBlockUser() {
        User newBlockedUser = new User("Username: anotherUser, Password: pass, UserID: 6, Description: Blocking Test, FriendsOnly: false; Friends: []; Blocked Users: []; Conversations: []");
        assertTrue(user.blockUser(newBlockedUser));
        assertTrue(user.getBlockList().contains(newBlockedUser));
    }

    @Test
    void testUnblockUser() {
        assertTrue(user.unblockUser(blockedUser));
        assertFalse(user.getBlockList().contains(blockedUser));
    }

    @Test
    void testIsUserBlocked() {
        assertTrue(user.isUserBlocked(blockedUser));
        assertFalse(user.isUserBlocked(friend1));
    }

    @Test
    void testToStringFormat() {
        String expectedOutput = "Username: testUser, Password: testPass, UserID: 1, Description: Test Description, FriendsOnly: false; " +
                "Friends: [friend1]; Blocked Users: [blockedUser]; Conversations: []";
        assertEquals(expectedOutput, user.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        User sameUser = new User("testUser", "testPass", 1, "Another Description", null, null, null);
        assertEquals(user, sameUser);
        assertEquals(user.hashCode(), sameUser.hashCode());

        User differentUser = new User("differentUser", "testPass", 2, "Different User", null, null, null);
        assertNotEquals(user, differentUser);
        assertNotEquals(user.hashCode(), differentUser.hashCode());
    }
}
