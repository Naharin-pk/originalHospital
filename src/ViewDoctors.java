import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewDoctors {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String username = "root";
        String password = "";

        try {

            Connection connection =
                    DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM doctor";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            System.out.println("Doctors:");

            while (result.next()) {

                int doctorId = result.getInt("doctor_id");
                String name = result.getString("name");
                String id = result.getString("id");
                String specialization = result.getString("specialization");
                String date = result.getString("date");
                String startTime = result.getString("start_time");
                String endTime = result.getString("end_time");
                int patientCount = result.getInt("patient_count");
                String department = result.getString("department");

                System.out.println("----------------------------");
                System.out.println("Doctor ID: " + doctorId);
                System.out.println("Name: " + name);
                System.out.println("ID: " + id);
                System.out.println("Specialization: " + specialization);
                System.out.println("Department: " + department);
                System.out.println("Date: " + date);
                System.out.println("Start Time: " + startTime);
                System.out.println("End Time: " + endTime);
                System.out.println("Patients: " + patientCount);
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}