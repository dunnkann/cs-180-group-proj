import java.io.*;
import java.net.*;

public class Server implements ServerInterface{
    
    public static void main(String[] args) {
        int port = 4242;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on " + port);

            while(true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new UserManager(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
