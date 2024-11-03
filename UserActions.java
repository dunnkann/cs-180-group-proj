public interface UserActions {
    // Add a friend to the user's friend list
    void addFriend(User friend);

    // Remove a friend from the user's friend list
    void removeFriend(User friend);

    // Block a user
    boolean blockUser(User u);

    // Unblock a user
    boolean unblockUser(User u);

    // Check if a user is blocked
    boolean isUserBlocked(User u);

    // Getters for user attributes
    String getUsername();
    String getPassword();
    int getUserId();
    String getDescription();
    boolean isFriendsOnly();

    // Setters for user attributes
    void setUsername(String username);
    void setPassword(String password);
    void setUserId(int userId);
    void setDescription(String description);
    void setFriendsOnly(boolean friendsOnly);
}
