import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserManager {
    private static final String USER_DATA_FILE = "users.txt"; // Store user data
    private AtomicInteger userIdCounter;

    public UserManager() {
        // Create the users.txt file if it does not exist
        try {
            new File(USER_DATA_FILE).createNewFile();
            userIdCounter = new AtomicInteger(getTotalUsers()); // Initialize userIdCounter with the total users
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) throws IOException {
        if (usernameExists(username)) {
            System.out.println("Username already exists.");
            return false;
        }

        User newUser = new User(username, password, userIdCounter); // Pass AtomicInteger
        userIdCounter.incrementAndGet(); // Increment userId after creating the user
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(newUser.getUsername() + "," + newUser.getPassword() + "," + newUser.getUserId());
            writer.newLine();
            return true;
        }
    }

    private boolean usernameExists(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String storedUsername = line.split(",")[0];
                if (storedUsername.equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean loginUser(String username, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String storedUsername = parts[0];
                String storedPassword = parts[1];

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    return true; // Login successful
                }
            }
        }
        return false; // Invalid credentials
    }

    private int getTotalUsers() throws IOException {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            while (reader.readLine() != null) {
                count++;
            }
        }
        return count;
    }
}
