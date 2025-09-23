import java.sql.Connection;
import java.sql.SQLException;
import util.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConn = DatabaseConnection.getInstance();

        try (Connection conn = dbConn.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to connect.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database:");
            e.printStackTrace();
        }
    }
}
