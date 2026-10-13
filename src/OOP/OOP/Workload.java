package OOP.OOP;

import java.time.LocalDate;

public class Workload {

    private LocalDate date;
    private double ratio;
    private double ratio1;
    private double ratio2;
    private double dailyavg;

    public Workload(LocalDate date,
                    double ratio,
                    double ratio1,
                    double ratio2,
                    double dailyavg) {

        this.date = date;
        this.ratio = ratio;
        this.ratio1 = ratio1;
        this.ratio2 = ratio2;
        this.dailyavg = dailyavg;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getRatio() {
        return ratio;
    }

    public double getRatio1() {
        return ratio1;
    }

    public double getRatio2() {
        return ratio2;
    }

    public double getDailyavg() {
        return dailyavg;
    }
}