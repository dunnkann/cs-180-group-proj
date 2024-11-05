import java.io.*;
import java.util.Scanner;
import javax.swing.*;


public class UserSearch implements UserSearchInterface {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String search;
        System.out.println("Search for User");
        search = scanner.nextLine();

        String results;
        results = Check(search);
        System.out.println(results);
        scanner.close();
    }
    public static String Check(String search) {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/users"))) {
            String newLine;
            while ((newLine = reader.readLine()) != null) {
                String[] usernames = newLine.split(",");
                for (String name : usernames) {
                    if (name.trim().equals(search)){
                        return "They exist";
                    }
                }
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }


        return "This user " + search + " does not exist";
    }
}
