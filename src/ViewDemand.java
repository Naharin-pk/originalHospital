import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewDemand {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String username = "root";
        String password = "";

        try {

            Connection connection =
                    DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM demand";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            System.out.println("Demand Records:");

            while (result.next()) {

                int demandId = result.getInt("demand_id");
                String surgeryName = result.getString("surgery_name");
                int performedCount = result.getInt("performed_count");
                int unmetCount = result.getInt("unmet_count");
                int equipmentCount = result.getInt("equipment_count");
                String department = result.getString("department");

                System.out.println("----------------------------");
                System.out.println("Demand ID: " + demandId);
                System.out.println("Surgery Name: " + surgeryName);
                System.out.println("Performed Count: " + performedCount);
                System.out.println("Unmet Count: " + unmetCount);
                System.out.println("Equipment Count: " + equipmentCount);
                System.out.println("Department: " + department);
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}