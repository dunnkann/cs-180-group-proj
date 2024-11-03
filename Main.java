// Main.java
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the User Management System!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter username: ");
                String newUsername = scanner.nextLine();
                System.out.print("Enter password: ");
                String newPassword = scanner.nextLine();
                try {
                    if (userManager.registerUser(newUsername, newPassword)) {
                        System.out.println("Registration successful!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.print("Enter username: ");
                String loginUsername = scanner.nextLine();
                System.out.print("Enter password: ");
                String loginPassword = scanner.nextLine();
                try {
                    if (userManager.loginUser(loginUsername, loginPassword)) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }

        scanner.close();
    }
}

