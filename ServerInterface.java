import java.io.*;
import java.net.*;

public interface ServerInterface {

    // Starts the server and listens for incoming client connections
    void startServer(int port) throws IOException;

    // Accepts a client connection and returns a Socket object representing that connection
    Socket acceptClientConnection() throws IOException;

    // Stops the server and cleans up resources (e.g., closes the server socket)
    void stopServer() throws IOException;
}
