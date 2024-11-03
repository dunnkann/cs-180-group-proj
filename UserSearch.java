import java.io.*;
import java.util.Scanner;


public class UserSearch {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String search = "";
        System.out.println("Search for User");
        search = scanner.nextLine();

        String results = "";
        results = Check(search);
        System.out.println(results);
        scanner.close();
    }
    public static String Check(String search) {
        String filePath = "C:\\Users\\morri\\UsersList.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String newLine;
            while ((newLine = reader.readLine()) != null) {
                if (newLine.equals(search)) {
                    return "They exist";
                    }
                }
            } catch(IOException e){
                throw new RuntimeException(e);
        }


        return "This user " + search + " does not exist";
    }
    }