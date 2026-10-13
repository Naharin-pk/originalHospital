package OOP.OOP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HospitalDatabaseConnector {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hospital_db";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL, USERNAME, PASSWORD
        );
    }

    public static void main(String[] args) {

        try (Connection connection = getConnection()) {
            System.out.println(
                    "Database connected successfully!"
            );

        } catch (SQLException e) {
            System.out.println(
                    "Database connection failed!"
            );
            e.printStackTrace();
        }
    }
}