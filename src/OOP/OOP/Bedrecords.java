package OOP.OOP;

public class Bedrecords {

    private int bedOccupied;
    private int bedTotal;
    private String month;
    private String department;

    public Bedrecords(String month, int bedOccupied, int bedTotal,
                      String department) {

        this.month = month;
        this.bedTotal = bedTotal;
        this.bedOccupied = bedOccupied;
        this.department = department;
    }

    public int getbedOccupied() {
        return bedOccupied;
    }

    public int getbedTotal() {
        return bedTotal;
    }

    public String getMonth() {
        return month;
    }

    public String getDepartment() {
        return department;
    }
}