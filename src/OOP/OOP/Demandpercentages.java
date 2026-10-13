package OOP.OOP;

public class Demandpercentages {

    private String surgeryName;
    private double unmetPercentage;
    private double performedPercentage;
    private int equipmentCount;
    private int totalDemand;

    public Demandpercentages(
            String surgeryName,
            double unmetPercentage,
            double performedPercentage,
            int equipmentCount,
            int totalDemand) {

        this.surgeryName = surgeryName;
        this.unmetPercentage = unmetPercentage;
        this.performedPercentage = performedPercentage;
        this.equipmentCount = equipmentCount;
        this.totalDemand = totalDemand;
    }

    public String getsurgeryName() {
        return surgeryName;
    }

    public double getunmetPercentage() {
        return unmetPercentage;
    }

    public double getperformedPercentage() {
        return performedPercentage;
    }

    public int getequipmentCount() {
        return equipmentCount;
    }

    public int gettotalDemand() {
        return totalDemand;
    }
}