// Main.java
import java.io.*;
//import java.util.Scanner;
import javax.swing.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class Main implements Runnable, MainInterface {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String message;
    private JPanel panel;
    private JFrame frame;
    private Main user;

    private JButton profileButton;
    private JButton friends;
    private JButton dmButton;
    private JButton searchButton;

    private JTextField searchBar;

    public Main() {

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == dmButton) {
                
            }
            if (e.getSource() == searchButton) {
                writer.write("Search");
                writer.println();
                writer.write(searchBar.getText());
                writer.println();
                writer.flush();
                search();
            }
        }
    };

    public void search() {
        try {
            String[] results = new String[Integer.parseInt(reader.readLine())];
            String[] options;
            for (int i = 0; i < results.length; i++) {
                results[i] = reader.readLine();
            }
            String selectedUser = (String) JOptionPane.showInputDialog(null, "Results", "Results", JOptionPane.QUESTION_MESSAGE, null, results, results[0]);
            writer.write(selectedUser);
            writer.println();
            writer.flush();
            options = new String[Integer.parseInt(reader.readLine())];
            for (int i = 0; i <options.length ; i++) {
                options[i] = reader.readLine();
            }
            String option = (String) JOptionPane.showInputDialog(null, selectedUser, "Options", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IOError", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void run() {
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
        Boolean success = false;
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
                        success = true;
                        
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
                        success = true;
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
        if (success) {
            frame = new JFrame();
            Container content = frame.getContentPane();
            content.setLayout(new BorderLayout());

            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);

            dmButton = new JButton();
            dmButton.addActionListener(actionListener);
            searchButton = new JButton("Search");
            searchButton.addActionListener(actionListener);

            searchBar = new JTextField("Search for User by Username",15);

            panel = new JPanel();
            panel.add(searchBar);
            panel.add(searchButton);
            content.add(panel, BorderLayout.NORTH);
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

    

}

