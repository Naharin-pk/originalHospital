package OOP.OOP;

import java.time.LocalDate;
import java.time.LocalTime;

public class Doctor {

    private String name;
    private String id;
    private String specialization;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int patientCount;
    private String department;

    public Doctor(String name, String id, String specialization,
                  LocalDate date, LocalTime startTime, LocalTime endTime,
                  int patientCount, String department) {

        this.name = name;
        this.id = id;
        this.specialization = specialization;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientCount = patientCount;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getpatientCount() {
        return patientCount;
    }

    public String getDepartment() {
        return department;
    }
}
