package OOP.OOP;

public class Bedpercentage {
    double percentage;
    String month;
    Bedpercentage(double percentage,String month)
    {
        this.month=month;
        this.percentage=percentage;
    }
    double getPercentage()
    {
        return percentage;
    }
    String getMonth()
    {
        return month;
    }
}