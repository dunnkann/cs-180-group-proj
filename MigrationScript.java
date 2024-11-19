import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImportUsers {

    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/cool_db"; // Adjust if needed
        String user = "postgres";  // Your DB username
        String password = "12345";  // Your DB password

        // File path to users.txt
        String filePath = "users.txt";  // Adjust path as needed

        // Database connection
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database.");

            // SQL query to insert user data into the usertable
            String sql = "INSERT INTO usertable (username, password, userid, description, friendsonly, conversations, friendlist, blocklist) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 BufferedReader br = new BufferedReader(new FileReader(filePath))) {

                String line;
                while ((line = br.readLine()) != null) {
                    // Split the line into fields (assuming space-separated values)
                    String[] fields = line.split(" ");  // Adjust if using a different delimiter

                    if (fields.length == 8) {  // Ensure there are 8 fields
                        String username = fields[0];
                        String passwordVal = fields[1];
                        int userid = Integer.parseInt(fields[2]);
                        String description = fields[3];
                        String friendsonly = fields[4];
                        String conversations = fields[5];
                        String friendlist = fields[6];
                        String blocklist = fields[7];

                        // Set values for the PreparedStatement
                        pstmt.setString(1, username);
                        pstmt.setString(2, passwordVal);
                        pstmt.setInt(3, userid);
                        pstmt.setString(4, description);
                        pstmt.setString(5, friendsonly);
                        pstmt.setString(6, conversations);
                        pstmt.setString(7, friendlist);
                        pstmt.setString(8, blocklist);

                        // Execute the insert query
                        pstmt.executeUpdate();
                    } else {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }

                System.out.println("Data transferred successfully.");

            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
