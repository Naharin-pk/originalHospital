package OOP.OOP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseLoader {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hospital_db";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "";

    public ArrayList<Doctor> loadDoctors() {

        ArrayList<Doctor> doctors = new ArrayList<>();

        try {

            Connection connection =
                    DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM doctor";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                String name = result.getString("name");
                String id = result.getString("id");
                String specialization =
                        result.getString("specialization");

                java.time.LocalDate date =
                        result.getDate("date").toLocalDate();

                java.time.LocalTime startTime =
                        result.getTime("start_time").toLocalTime();

                java.time.LocalTime endTime =
                        result.getTime("end_time").toLocalTime();

                int patientCount =
                        result.getInt("patient_count");

                String department =
                        result.getString("department");

                Doctor doctor = new Doctor(
                        name,
                        id,
                        specialization,
                        date,
                        startTime,
                        endTime,
                        patientCount,
                        department
                );

                doctors.add(doctor);
            }

            connection.close();

        } catch (Exception e) {

            System.out.println("Error loading doctor data.");
            e.printStackTrace();
        }

        return doctors;
    }
    public ArrayList<Bedrecords> loadBedRecords() {

        ArrayList<Bedrecords> bedRecords = new ArrayList<>();

        try {

            Connection connection =
                    DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM bed";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                String month =
                        result.getString("month");

                int bedOccupied =
                        result.getInt("bed_occupied");

                int bedTotal =
                        result.getInt("bed_total");

                String department =
                        result.getString("department");

                Bedrecords bed = new Bedrecords(
                        month,
                        bedOccupied,
                        bedTotal,
                        department
                );

                bedRecords.add(bed);
            }

            connection.close();

        } catch (Exception e) {

            System.out.println("Error loading bed data.");
            e.printStackTrace();
        }

        return bedRecords;
    }
    public ArrayList<Demand> loadDemand() {

        ArrayList<Demand> demandList = new ArrayList<>();

        try {

            Connection connection =
                    DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM demand";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                String surgeryName =
                        result.getString("surgery_name");

                int performedCount =
                        result.getInt("performed_count");

                int unmetCount =
                        result.getInt("unmet_count");

                int equipmentCount =
                        result.getInt("equipment_count");

                String department =
                        result.getString("department");

                Demand demand = new Demand(
                        surgeryName,
                        performedCount,
                        unmetCount,
                        equipmentCount,
                        department
                );

                demandList.add(demand);
            }

            connection.close();

        } catch (Exception e) {

            System.out.println("Error loading demand data.");
            e.printStackTrace();
        }

        return demandList;
    }
    public static void main(String[] args) {

        DatabaseLoader loader = new DatabaseLoader();

        ArrayList<Doctor> doctors = loader.loadDoctors();

        System.out.println("Doctors loaded: " + doctors.size());

        for (Doctor doctor : doctors) {

            System.out.println("----------------------");
            System.out.println("Name: " + doctor.getName());
            System.out.println("ID: " + doctor.getId());
            System.out.println("Department: " + doctor.getDepartment());
            System.out.println("Patients: " + doctor.getpatientCount());
        }
        ArrayList<Bedrecords> bedRecords = loader.loadBedRecords();

        System.out.println("\nBed records loaded: " + bedRecords.size());

        for (Bedrecords bed : bedRecords) {

            System.out.println("----------------------");
            System.out.println("Month: " + bed.getMonth());
            System.out.println("Beds Occupied: " + bed.getbedOccupied());
            System.out.println("Total Beds: " + bed.getbedTotal());
            System.out.println("Department: " + bed.getDepartment());
        }
        ArrayList<Demand> demandList = loader.loadDemand();

        System.out.println("\nDemand records loaded: " + demandList.size());

        for (Demand demand : demandList) {

            System.out.println("----------------------");
            System.out.println("Surgery: " + demand.getsurgeryName());
            System.out.println("Performed: " + demand.getperformedCount());
            System.out.println("Unmet: " + demand.getunmetCount());
            System.out.println("Equipment: " + demand.getequipmentCount());
            System.out.println("Department: " + demand.getDepartment());
        }
    }
}