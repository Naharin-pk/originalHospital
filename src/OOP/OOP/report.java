
package OOP.OOP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

// Parent class for reports
abstract class report {
    public abstract String generateReport() throws SQLException;

    public abstract String showInference() throws SQLException;
}

// Generates reports using records from the selected department
class HospitalReport extends report {

    private final Connection connection;
    private final String department;
    private final int minimumExpectedCases;

    HospitalReport(Connection connection, String department,
                   int minimumExpectedCases) {
        this.connection = connection;
        this.department = department;
        this.minimumExpectedCases = minimumExpectedCases;
    }

    @Override
    public String generateReport() throws SQLException {
        StringBuilder sb = new StringBuilder();

        sb.append("HOSPITAL RESOURCE AND SERVICE DEMAND REPORT\n");
        sb.append("Department: ").append(department).append("\n");
        sb.append("====================================================\n\n");

        appendDoctorReport(sb);
        appendWorkloadSlotAnalysis(sb);
        appendBedReport(sb);
        appendDemandReport(sb);

        return sb.toString();
    }

    @Override
    public String showInference() throws SQLException {
        StringBuilder sb = new StringBuilder();

        sb.append("ANALYSIS AND INFERENCE\n");
        sb.append("====================================================\n\n");

        appendDoctorInference(sb);
        appendBedInference(sb);
        appendDemandInference(sb);

        return sb.toString();
    }

    // 1. Doctor workload details
    private void appendDoctorReport(StringBuilder sb) throws SQLException {
        sb.append("1. DOCTOR WORKLOAD\n");
        sb.append("------------------------------\n");

        String sql = "SELECT name, specialization, date, start_time, " +
                "end_time, patient_count FROM doctor " +
                "WHERE department = ? ORDER BY date, start_time";

        int records = 0;
        long totalPatients = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, department);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records++;

                    int patients = rs.getInt("patient_count");
                    totalPatients += patients;

                    sb.append("Doctor: ")
                            .append(rs.getString("name")).append("\n");

                    sb.append("Specialization: ")
                            .append(rs.getString("specialization")).append("\n");

                    sb.append("Date: ")
                            .append(rs.getDate("date")).append("\n");

                    sb.append("Time: ")
                            .append(rs.getTime("start_time"))
                            .append(" - ")
                            .append(rs.getTime("end_time")).append("\n");

                    sb.append("Patients attended: ")
                            .append(patients).append("\n");

                    sb.append("----------------------------------------------\n");
                }
            }
        }

        if (records == 0) {
            sb.append("No doctor records found for this department.\n");
        }

        sb.append("Total doctor records: ").append(records).append("\n");
        sb.append("Total patients attended: ")
                .append(totalPatients).append("\n\n");
    }

    // 2. Workload analysis using the three time slots in Analysis.java
    private void appendWorkloadSlotAnalysis(StringBuilder sb)
            throws SQLException {

        sb.append("WORKLOAD ANALYSIS BY TIME SLOT\n");
        sb.append("------------------------------\n");

        class SlotTotals {
            int count9to12 = 0;
            int patients9to12 = 0;

            int count12to15 = 0;
            int patients12to15 = 0;

            int count15to18 = 0;
            int patients15to18 = 0;
        }

        Map<LocalDate, SlotTotals> dailyTotals = new LinkedHashMap<>();

        String sql = "SELECT date, start_time, end_time, patient_count " +
                "FROM doctor WHERE department = ? ORDER BY date, start_time";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, department);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LocalDate date = rs.getDate("date").toLocalDate();

                    LocalTime start =
                            rs.getTime("start_time").toLocalTime();

                    LocalTime end =
                            rs.getTime("end_time").toLocalTime();

                    int patients = rs.getInt("patient_count");

                    SlotTotals totals = dailyTotals.computeIfAbsent(
                            date, d -> new SlotTotals()
                    );

                    if (start.equals(LocalTime.of(9, 0))
                            && end.equals(LocalTime.of(12, 0))) {

                        totals.count9to12++;
                        totals.patients9to12 += patients;

                    } else if (start.equals(LocalTime.of(12, 0))
                            && end.equals(LocalTime.of(15, 0))) {

                        totals.count12to15++;
                        totals.patients12to15 += patients;

                    } else if (start.equals(LocalTime.of(15, 0))
                            && end.equals(LocalTime.of(18, 0))) {

                        totals.count15to18++;
                        totals.patients15to18 += patients;
                    }
                }
            }
        }

        if (dailyTotals.isEmpty()) {
            sb.append("No doctor records available for workload analysis.\n\n");
            return;
        }

        for (Map.Entry<LocalDate, SlotTotals> entry
                : dailyTotals.entrySet()) {

            SlotTotals t = entry.getValue();

            double ratio9to12 = t.count9to12 == 0
                    ? 0.0
                    : (double) t.patients9to12 / t.count9to12;

            double ratio12to15 = t.count12to15 == 0
                    ? 0.0
                    : (double) t.patients12to15 / t.count12to15;

            double ratio15to18 = t.count15to18 == 0
                    ? 0.0
                    : (double) t.patients15to18 / t.count15to18;

            // Daily average follows the calculation in Analysis.java
            double dailyAverage =
                    (ratio9to12 + ratio12to15 + ratio15to18) / 3.0;

            sb.append("Date: ").append(entry.getKey()).append("\n");

            sb.append(String.format(
                    Locale.US,
                    "9:00-12:00 average patients per doctor: %.2f%n",
                    ratio9to12
            ));

            sb.append(String.format(
                    Locale.US,
                    "12:00-15:00 average patients per doctor: %.2f%n",
                    ratio12to15
            ));

            sb.append(String.format(
                    Locale.US,
                    "15:00-18:00 average patients per doctor: %.2f%n",
                    ratio15to18
            ));

            sb.append(String.format(
                    Locale.US,
                    "Daily average workload ratio: %.2f%n",
                    dailyAverage
            ));

            sb.append("----------------------------------------------\n");
        }

        sb.append("\n");
    }

    // 3. Bed occupancy details
    private void appendBedReport(StringBuilder sb) throws SQLException {
        sb.append("3. BED OCCUPANCY\n");
        sb.append("------------------------------\n");

        String sql = "SELECT month, bed_occupied, bed_total FROM bed " +
                "WHERE department = ? ORDER BY month";

        int records = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, department);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records++;

                    String month = rs.getString("month");
                    int occupied = rs.getInt("bed_occupied");
                    int total = rs.getInt("bed_total");

                    double percentage = total > 0
                            ? occupied * 100.0 / total
                            : 0.0;

                    sb.append("Month: ").append(month).append("\n");
                    sb.append("Beds occupied: ").append(occupied).append("\n");
                    sb.append("Total beds: ").append(total).append("\n");

                    sb.append(String.format(
                            Locale.US,
                            "Occupancy: %.2f%%%n",
                            percentage
                    ));

                    sb.append("----------------------------------------------\n");
                }
            }
        }

        if (records == 0) {
            sb.append("No bed records found for this department.\n");
        }

        sb.append("Total bed records: ").append(records).append("\n\n");
    }

    // 4. Surgery demand details
    private void appendDemandReport(StringBuilder sb) throws SQLException {
        sb.append("4. SURGERY DEMAND\n");
        sb.append("------------------------------\n");

        String sql = "SELECT surgery_name, performed_count, unmet_count, " +
                "equipment_count FROM demand WHERE department = ? " +
                "ORDER BY surgery_name";

        int records = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, department);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records++;

                    String surgery = rs.getString("surgery_name");
                    int performed = rs.getInt("performed_count");
                    int unmet = rs.getInt("unmet_count");
                    int equipment = rs.getInt("equipment_count");

                    int total = performed + unmet;

                    double performedPercent = total > 0
                            ? performed * 100.0 / total
                            : 0.0;

                    double unmetPercent = total > 0
                            ? unmet * 100.0 / total
                            : 0.0;

                    sb.append("Surgery: ").append(surgery).append("\n");
                    sb.append("Performed: ").append(performed).append("\n");
                    sb.append("Unmet demand: ").append(unmet).append("\n");
                    sb.append("Total demand: ").append(total).append("\n");

                    sb.append(String.format(
                            Locale.US,
                            "Performed percentage: %.2f%%%n",
                            performedPercent
                    ));

                    sb.append(String.format(
                            Locale.US,
                            "Unmet percentage: %.2f%%%n",
                            unmetPercent
                    ));

                    sb.append("Equipment count: ")
                            .append(equipment).append("\n");

                    sb.append("----------------------------------------------\n");
                }
            }
        }

        if (records == 0) {
            sb.append("No demand records found for this department.\n");
        }

        sb.append("Total demand records: ").append(records).append("\n\n");
    }

    // Doctor workload inference: more than 30 patients means high workload
    private void appendDoctorInference(StringBuilder sb)
            throws SQLException {

        sb.append("DOCTOR WORKLOAD INFERENCE\n");

        String sql = "SELECT name, date, start_time, end_time, patient_count " +
                "FROM doctor WHERE department = ? ORDER BY date, start_time";

        boolean foundHighWorkload = false;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, department);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int patients = rs.getInt("patient_count");

                    if (patients > 30) {
                        foundHighWorkload = true;

                        sb.append("High workload: ")
                                .append(rs.getString("name"))
                                .append(" on ")
                                .append(rs.getDate("date"))
                                .append(" (")
                                .append(rs.getTime("start_time"))
                                .append(" - ")
                                .append(rs.getTime("end_time"))
                                .append("), patients: ")
                                .append(patients).append("\n");
                    }
                }
            }
        }

        if (!foundHighWorkload) {
            sb.append(
                    "No high workload detected using the 30-patient threshold.\n"
            );
        }

        sb.append("\n");
    }

    // Bed occupancy inference
    private void appendBedInference(StringBuilder sb) throws SQLException {
        sb.append("BED OCCUPANCY INFERENCE\n");

        String sql = "SELECT month, bed_occupied, bed_total FROM bed " +
                "WHERE department = ? ORDER BY month";

        boolean found = false;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, department);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    found = true;

                    int occupied = rs.getInt("bed_occupied");
                    int total = rs.getInt("bed_total");

                    double percentage = total > 0
                            ? occupied * 100.0 / total
                            : 0.0;

                    sb.append(rs.getString("month")).append(": ");

                    if (percentage > 90) {
                        sb.append("Very high occupancy");
                    } else if (percentage >= 70) {
                        sb.append("High occupancy");
                    } else {
                        sb.append("Normal occupancy");
                    }

                    sb.append(String.format(
                            Locale.US,
                            " (%.2f%%)%n",
                            percentage
                    ));
                }
            }
        }

        if (!found) {
            sb.append("No bed records available for inference.\n");
        }

        sb.append("\n");
    }

    // Surgery demand inference using the existing high unmet-demand condition
    private void appendDemandInference(StringBuilder sb)
            throws SQLException {

        sb.append("SURGERY DEMAND INFERENCE\n");

        String sql = "SELECT surgery_name, performed_count, unmet_count " +
                "FROM demand WHERE department = ? ORDER BY surgery_name";

        boolean found = false;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, department);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    found = true;

                    String surgery = rs.getString("surgery_name");
                    int performed = rs.getInt("performed_count");
                    int unmet = rs.getInt("unmet_count");

                    int total = performed + unmet;

                    double unmetPercent = total > 0
                            ? unmet * 100.0 / total
                            : 0.0;

                    sb.append(surgery).append(": ");

                    // High unmet-demand condition from the existing code:
                    // total demand must exceed the threshold AND
                    // more than 50% of the demand must remain unmet.
                    if (total > minimumExpectedCases && unmetPercent > 50) {
                        sb.append("High unmet demand detected. ")
                                .append(
                                        "This may indicate a capacity constraint."
                                );
                    } else {
                        sb.append("No high unmet demand detected.");
                    }

                    sb.append(String.format(
                            Locale.US,
                            " (unmet: %.2f%%)%n",
                            unmetPercent
                    ));
                }
            }
        }

        if (!found) {
            sb.append("No demand records available for inference.\n");
        }

        sb.append("\n");
    }
}
