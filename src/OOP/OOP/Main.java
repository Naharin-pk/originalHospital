package OOP.OOP;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        DatabaseLoader loader = new DatabaseLoader();

        Department department =
                new Department("Orthropedics");

        // Load doctors from database
        ArrayList<Doctor> doctors =
                loader.loadDoctors();

        for (Doctor doctor : doctors) {

            if (doctor.getDepartment()
                    .equalsIgnoreCase(department.getName())) {

                department.addDoctor(doctor);
            }
        }

        // Load bed records from database
        ArrayList<Bedrecords> beds =
                loader.loadBedRecords();

        for (Bedrecords bed : beds) {

            if (bed.getDepartment()
                    .equalsIgnoreCase(department.getName())) {

                department.addBedrecord(bed);
            }
        }

        // Load demand records from database
        ArrayList<Demand> demands =
                loader.loadDemand();

        for (Demand demand : demands) {

            if (demand.getDepartment()
                    .equalsIgnoreCase(department.getName())) {

                department.addDemand(demand);
            }
        }

        // Display department information
        System.out.println("Department: "
                + department.getName());

        System.out.println("Doctors: "
                + department.getDoctors().size());

        System.out.println("Bed Records: "
                + department.getBedrecords().size());

        System.out.println("Demand Records: "
                + department.getDemand().size());
        department.performAnalysis();
    }
}
