import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewBeds {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String username = "root";
        String password = "";

        try {

            Connection connection =
                    DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM bed";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            System.out.println("Bed Records:");

            while (result.next()) {

                int bedId = result.getInt("bed_id");
                String month = result.getString("month");
                int bedOccupied = result.getInt("bed_occupied");
                int bedTotal = result.getInt("bed_total");
                String department = result.getString("department");

                System.out.println("----------------------------");
                System.out.println("Bed ID: " + bedId);
                System.out.println("Month: " + month);
                System.out.println("Beds Occupied: " + bedOccupied);
                System.out.println("Total Beds: " + bedTotal);
                System.out.println("Department: " + department);
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
