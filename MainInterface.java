public interface MainInterface {
    
    // Method to establish a socket connection to the server
    void connect(String host, int port) throws IOException;

    // Method to register a new user
    boolean register(String username, String password) throws IOException;

    // Method to log in an existing user
    boolean login(String username, String password) throws IOException;

    // Method to perform a search for a user
    void search() throws IOException;

    // Method to send data (message or command) to the server
    void sendData(String data) throws IOException;

    // Method to receive data (message or response) from the server
    String receiveData() throws IOException;

    // Method to close the connection (socket)
    void closeConnection() throws IOException;
}
