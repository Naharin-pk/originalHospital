package OOP.OOP;

public class Demand {

    private String surgeryName;
    private int performedCount;
    private int unmetCount;
    private int equipmentCount;
    private String department;

    public Demand(String surgeryName, int performedCount, int unmetCount,
                  int equipmentCount, String department) {

        this.surgeryName = surgeryName;
        this.performedCount = performedCount;
        this.unmetCount = unmetCount;
        this.equipmentCount = equipmentCount;
        this.department = department;
    }

    public String getsurgeryName() {
        return surgeryName;
    }

    public int getperformedCount() {
        return performedCount;
    }

    public int getunmetCount() {
        return unmetCount;
    }

    public int getequipmentCount() {
        return equipmentCount;
    }

    public String getDepartment() {
        return department;
    }

    public int gettotalDemand() {
        return unmetCount + performedCount;
    }
}
