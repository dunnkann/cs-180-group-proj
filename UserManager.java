// UserManager.java
import java.io.*;

public class UserManager {
    private static final String USER_DATA_FILE = "users.txt"; // Store user data

    public boolean registerUser(String username, String password) throws IOException {
        if (usernameExists(username)) {
            System.out.println("Username already exists.");
            return false;
        }

        User newUser = new User(username, password);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(newUser.getUsername() + "," + newUser.getPassword());
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
}
