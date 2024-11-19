// Main.java
import java.io.*;
//import java.util.Scanner;
import javax.swing.*;
import java.net.*;

public class Main implements Runnable{
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static String message;
    private static JPanel panel; 
    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        int choice;
        String password;
        String username;
        JOptionPane.showMessageDialog(null, "Welcome to the User Management System!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
        //System.out.println("Welcome to the User Management System!");
        try {
        socket = new Socket("localhost", 4242);// set up socket
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // set up reader
        writer = new PrintWriter(socket.getOutputStream()); // set up writer
        choice = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter 1 to register or 2 to Login", "Login", JOptionPane.QUESTION_MESSAGE));
        switch (choice) {
            case 1:
                username = JOptionPane.showInputDialog(null, "Enter Username:", "Login", JOptionPane.QUESTION_MESSAGE);
                password = JOptionPane.showInputDialog(null, "Enter Password:", "Login", JOptionPane.QUESTION_MESSAGE);
                try {
                    writer.write("Register");
                    writer.println();
                    writer.write(username);
                    writer.println();
                    writer.write(password);
                    writer.println();
                    writer.flush();
                    if (reader.readLine().equals("true")) {
                        JOptionPane.showMessageDialog(null, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Registration Failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occured during registration", "Error", JOptionPane.ERROR_MESSAGE);
                    
                }
                break;
            case 2:
                username = JOptionPane.showInputDialog(null, "Enter Username:", "Login", JOptionPane.QUESTION_MESSAGE);
                password = JOptionPane.showInputDialog(null, "Enter Password:", "Login", JOptionPane.QUESTION_MESSAGE);
                try {
                    writer.write("Login");
                    writer.println();
                    writer.write(username);
                    writer.println();
                    writer.write(password);
                    writer.println();
                    writer.flush();
                    if ((message = reader.readLine()).equals("true")) {
                        JOptionPane.showMessageDialog(null, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);

                    }
                    //System.out.println(message);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "An error occured during login", "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                break;
            default:
            JOptionPane.showMessageDialog(null, "Invalid Input","Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
        
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input","Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failure to Connect","Error", JOptionPane.ERROR_MESSAGE);
        }
        // System.out.println("1. Register");
        // System.out.println("2. Login");
        // int choice = scanner.nextInt();
        //scanner.nextLine(); // Consume newline

        

        // scanner.close();
    }

    public void run() {
        panel = new JPanel();
        
    }
}

