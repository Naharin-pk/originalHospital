
package OOP.OOP;

import java.util.ArrayList;

public class Department {

    private String name;
    private ArrayList<Doctor> doctors;
    private ArrayList<Bedrecords> bedrecords;
    private ArrayList<Demand> demand;

    public Department(String name) {
        this.name = name;
        this.doctors = new ArrayList<>();
        this.bedrecords = new ArrayList<>();
        this.demand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Bedrecords> getBedrecords() {
        return bedrecords;
    }

    public ArrayList<Demand> getDemand() {
        return demand;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void addBedrecord(Bedrecords bed) {
        bedrecords.add(bed);
    }

    public void addDemand(Demand demand) {
        this.demand.add(demand);
    }

    public void performAnalysis() {

        Analysis analysis = new Analysis();

        // Workload Analysis
        ArrayList<Workload> workloads =
                analysis.demandAnalysis(doctors, name);

        // Bed Occupancy Analysis
        ArrayList<Bedpercentage> bedPercentages =
                analysis.demandAnalysis(name, bedrecords);

        System.out.println("\n=== Workload Analysis ===");

        for (Workload workload : workloads) {

            System.out.println("----------------------");
            System.out.println("Date: " + workload.getDate());

            System.out.printf(
                    "9 AM - 12 PM Ratio: %.2f%n",
                    workload.getRatio()
            );

            System.out.printf(
                    "12 PM - 3 PM Ratio: %.2f%n",
                    workload.getRatio1()
            );

            System.out.printf(
                    "3 PM - 6 PM Ratio: %.2f%n",
                    workload.getRatio2()
            );

            System.out.printf(
                    "Daily Average: %.2f%n",
                    workload.getDailyavg()
            );
        }

        // Bed Occupancy Analysis
        System.out.println("\n=== Bed Occupancy Analysis ===");

        for (int i = 0; i < bedPercentages.size(); i++) {

            System.out.println("----------------------");
            System.out.println(
                    "Month: " + bedrecords.get(i).getMonth()
            );

            double percentage =
                    bedPercentages.get(i).getPercentage();

            System.out.printf(
                    "Bed Occupancy: %.2f%%%n",
                    percentage
            );

            if (percentage > 90) {
                System.out.println("Inference: Very high bed occupancy");
            } else if (percentage >= 70) {
                System.out.println("Inference: High bed occupancy");
            } else {
                System.out.println("Inference: Normal bed occupancy");
            }
        }

        // Demand Analysis
        System.out.println("\n=== Demand Analysis ===");

        for (Demand d : demand) {

            int performed = d.getperformedCount();
            int unmet = d.getunmetCount();
            int totalDemand = performed + unmet;

            double performedPercentage = 0;
            double unmetPercentage = 0;

            if (totalDemand > 0) {
                performedPercentage =
                        performed * 100.0 / totalDemand;

                unmetPercentage =
                        unmet * 100.0 / totalDemand;
            }

            System.out.println("----------------------");

            System.out.println(
                    "Surgery: " + d.getsurgeryName().trim()
            );

            System.out.println("Total Demand: " + totalDemand);

            System.out.printf(
                    "Performed: %.2f%%%n",
                    performedPercentage
            );

            System.out.printf(
                    "Unmet: %.2f%%%n",
                    unmetPercentage
            );

            System.out.println(
                    "Equipment Count: " + d.getequipmentCount()
            );

            // High unmet-demand condition
            if (totalDemand > 30 && unmetPercentage > 50) {
                System.out.println(
                        "HIGH UNMET DEMAND - Capacity constraint may exist."
                );
            } else {
                System.out.println(
                        "No high unmet demand detected."
                );
            }
        }
    }
}
