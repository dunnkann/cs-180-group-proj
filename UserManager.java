import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.net.*;
import java.util.*;

public class UserManager implements UserManagerInterface, Runnable {
    private static final String USER_DATA_FILE = "users.txt"; // Store user data
    private AtomicInteger userIdCounter;
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String message;
    private User user; // user this thread is interacting with
    private List<User> users = Collections.synchronizedList(new ArrayList<>());

    public UserManager(Socket client) {
        try {
            this.clientSocket = client;
            // Create the users.txt file if it does not exist
            new File(USER_DATA_FILE).createNewFile();
            
            // Initialize userIdCounter with the highest existing user ID + 1
            userIdCounter = new AtomicInteger(getHighestUserId() + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean readUserFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                User user = new User(line);
                users.add(user);
            }
            return !users.isEmpty();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void run() {
        this.readUserFile();
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream());

            while((message = reader.readLine()) != null) {
                if (message.equals("Register")) {
                    boolean good = this.registerUser(reader.readLine(), reader.readLine());
                    writer.write(Boolean.toString(good));
                    writer.println();
                    writer.flush();
                } else if (message.equals("Login")) {
                    boolean good = this.loginUser(reader.readLine(), reader.readLine());
                    writer.write(Boolean.toString(good));
                    writer.println();
                    writer.flush();
                } else if (message.equals("Search")) {
                    List<User> userResults = this.searchUsers(reader.readLine());
                    writer.write(Integer.toString(userResults.size()));
                    writer.println();
                    writer.flush();
                    for (User user: userResults) {
                        writer.write(user.getUsername());
                        writer.println();
                        writer.flush();
                    }

                    String selectedUser = reader.readLine();
                    List<User> friends = user.getFriendList();
                    List<String> options = new ArrayList<String>();
                    boolean found = false;
                    options.add("Message");
                    for (User user: friends) {
                        
                        if (user.getUsername().equals(selectedUser)) {
                            options.add("Unfriend");
                            found = true;
                            
                            boolean blocked = false;
                            for (User blockedUser : this.user.getBlockList()) {
                                if (blockedUser.getUsername().equals(selectedUser)) {
                                    options.add("Unblock");
                                    blocked = true;
                                    break;
                                }
                            }

                            if (!blocked) {
                                options.add("Block");
                                break;
                            }
                        }

                    }

                    if (!found) {
                        options.add("Friend");
                    }
                    writer.write(Integer.toString(options.size()));
                    writer.println();
                    writer.flush();
                    for (String option : options) {
                        writer.write(option);
                        writer.println();
                        writer.flush();
                    }


                }
            }
        } catch(IOException e) {

        }
    }
    public boolean registerUser(String username, String password) throws IOException {
        if (usernameExists(username)) {
            System.out.println("Username already exists.");
            return false;
        }
        if (usernameHasForbiddenCharacter(username) || usernameHasForbiddenCharacter(password)) {
            System.out.println("Username or password cannot have a comma or colon");
            return false;
        } 
        user = new User(username, password, userIdCounter.get()); // Retrieve integer value from AtomicInteger
        userIdCounter.incrementAndGet(); // Increment userId after creating the user

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            // newUser.getUsername() + "," + newUser.getPassword() + "," + newUser.getUserId()
            writer.write(user.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing user data: " + e.getMessage());
            return false;
        }
    }
    public boolean usernameHasForbiddenCharacter(String username) {
        if (username.contains(",") || username.contains(":")) {
            return true;
        }
        return false;
    }
    public boolean usernameExists(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String storedUsername = line.split(",")[0];
                if (storedUsername.equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data: " + e.getMessage());
            throw e;
        }
        return false;
    }

    public boolean loginUser(String username, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String storedUsername = parts[0].split(":")[1].trim();
                String storedPassword = parts[1].split(":")[1].trim();

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    this.user = new User(line);
                    return true; // Login successful
                    
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data: " + e.getMessage());
            throw e;
        }
        return false; // Invalid credentials
    }
    public int getTotalUsers() {
        return 1;
    }
    private int getHighestUserId() throws IOException {
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int userId = Integer.parseInt(parts[2].split(":")[1].trim());
                if (userId > maxId) {
                    maxId = userId;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data: " + e.getMessage());
            throw e;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing user ID: " + e.getMessage());
        }
        return maxId;
    }

    public List<User> searchUsers(String username) {
        List<User> users = new ArrayList<User>();
        for (User user : this.users) {
            if (user.getUsername().indexOf(username) != -1) {
                users.add(user);
            }
        }
        return users;
    }
}
