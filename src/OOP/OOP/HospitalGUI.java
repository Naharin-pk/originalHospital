
package OOP.OOP;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class HospitalGUI {

    JFrame frame;
    String selectedDepartment;
    private final Color backgroundColor = new Color(242, 246, 252);
    private final Color primaryColor = new Color(35, 80, 130);
    private final Color purpleColor = new Color(110, 90, 170);
    private final Color greenColor = new Color(45, 145, 100);
    private final Color blueColor = new Color(35, 125, 165);

    // DATABASE CONNECTION
    Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String username = "root";
        String password = ""; // Enter your MySQL password if required

        return DriverManager.getConnection(url, username, password);
    }

    // FIRST SCREEN
    HospitalGUI() {
        frame = new JFrame("Hospital Resource and Service Demand Analytics");
        frame.setSize(850, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        showHome();
        frame.setVisible(true);
    }

    // REUSABLE TITLE
    JLabel createTitle(String text) {
        JLabel title = new JLabel(text, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(primaryColor);
        return title;
    }

    // REUSABLE BUTTON
    JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color.BLUE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(165, 38));
        return button;
    }

    // REUSABLE FIELD
    void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JTextField field
    ) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(fieldLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    // FIRST PAGE: HOME / DEPARTMENT SELECTION
    void showHome() {
        frame.getContentPane().removeAll();

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(35, 45, 35, 45)
        );

        JPanel headingPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headingPanel.setBackground(backgroundColor);

        JLabel title = createTitle("HOSPITAL ANALYSIS SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitle = new JLabel(
                "Hospital Resource and Service Demand Analytics",
                SwingConstants.CENTER
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(new Color(90, 105, 125));

        headingPanel.add(title);
        headingPanel.add(subtitle);

        mainPanel.add(headingPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(215, 225, 240)
                        ),
                        BorderFactory.createEmptyBorder(30, 35, 30, 35)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 12, 14, 12);

        JLabel chooseLabel = new JLabel("Select a Department");
        chooseLabel.setFont(new Font("Arial", Font.BOLD, 20));
        chooseLabel.setForeground(primaryColor);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(chooseLabel, gbc);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(departmentLabel, gbc);

        String[] departments = {
                "Orthopedics",
                "Neurology",
                "Gynecology"
        };

        JComboBox<String> departmentBox =
                new JComboBox<>(departments);
        departmentBox.setFont(new Font("Arial", Font.PLAIN, 15));
        departmentBox.setPreferredSize(new Dimension(230, 38));

        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(departmentBox, gbc);

        JButton openButton = createButton("OPEN DEPARTMENT", primaryColor);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(openButton, gbc);

        openButton.addActionListener(e -> {
            selectedDepartment =
                    (String) departmentBox.getSelectedItem();

            showDepartment();
        });

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "Doctor Workload  |  Bed Occupancy  |  Surgery Demand",
                SwingConstants.CENTER
        );
        footer.setForeground(new Color(90, 105, 125));
        footer.setFont(new Font("Arial", Font.PLAIN, 13));

        mainPanel.add(footer, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    // DOCTOR DETAILS PAGE
    void showDepartment() {
        frame.getContentPane().removeAll();

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        mainPanel.add(
                createTitle(selectedDepartment + " - Doctor Details"),
                BorderLayout.NORTH
        );

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(215, 225, 240)
                        ),
                        BorderFactory.createEmptyBorder(20, 30, 20, 30)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(9, 12, 9, 12);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField doctorName = new JTextField(20);
        JTextField doctorID = new JTextField(20);
        JTextField specialization = new JTextField(20);
        JTextField date = new JTextField(20);
        JTextField startTime = new JTextField(20);
        JTextField endTime = new JTextField(20);
        JTextField patientCount = new JTextField(20);

        addField(form, gbc, 0, "Doctor Name:", doctorName);
        addField(form, gbc, 1, "Doctor ID:", doctorID);
        addField(form, gbc, 2, "Specialization:", specialization);
        addField(form, gbc, 3, "Date (yyyy-MM-dd):", date);
        addField(form, gbc, 4, "Start Time (HH:mm):", startTime);
        addField(form, gbc, 5, "End Time (HH:mm):", endTime);
        addField(form, gbc, 6, "Patients Attended:", patientCount);

        mainPanel.add(form, BorderLayout.CENTER);

        JButton backButton = createButton("HOME", purpleColor);
        JButton nextButton = createButton("NEXT: BED DETAILS", blueColor);

        JPanel buttons = new JPanel();
        buttons.setBackground(backgroundColor);
        buttons.add(backButton);
        buttons.add(nextButton);

        mainPanel.add(buttons, BorderLayout.SOUTH);

        backButton.addActionListener(e -> showHome());

        nextButton.addActionListener(e -> {
            try {
                String name = doctorName.getText().trim();
                String id = doctorID.getText().trim();
                String spec = specialization.getText().trim();
                String dateText = date.getText().trim();
                String startText = startTime.getText().trim();
                String endText = endTime.getText().trim();
                String patientsText = patientCount.getText().trim();

                if (name.isEmpty() || id.isEmpty() || spec.isEmpty()
                        || dateText.isEmpty() || startText.isEmpty()
                        || endText.isEmpty() || patientsText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Please fill in all doctor details."
                    );
                    return;
                }

                LocalDate doctorDate = LocalDate.parse(dateText);
                LocalTime start = LocalTime.parse(startText);
                LocalTime end = LocalTime.parse(endText);
                int patients = Integer.parseInt(patientsText);

                if (patients < 0 || !end.isAfter(start)) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Enter a non-negative patient count and valid time slot."
                    );
                    return;
                }

                String sql = "INSERT INTO doctor " +
                        "(name, id, specialization, date, start_time, " +
                        "end_time, patient_count, department) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (Connection con = getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setString(1, name);
                    ps.setString(2, id);
                    ps.setString(3, spec);
                    ps.setDate(4, java.sql.Date.valueOf(doctorDate));
                    ps.setTime(5, java.sql.Time.valueOf(start));
                    ps.setTime(6, java.sql.Time.valueOf(end));
                    ps.setInt(7, patients);
                    ps.setString(8, selectedDepartment);

                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(
                        frame,
                        "Doctor details saved successfully!"
                );

                beds();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Patient count must be a whole number."
                );
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Use date yyyy-MM-dd and time HH:mm."
                );
            } catch (SQLException ex) {
                showDatabaseError(ex);
            }
        });

        frame.setContentPane(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    // BED DETAILS PAGE
    void beds() {
        frame.getContentPane().removeAll();

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        mainPanel.add(
                createTitle(selectedDepartment + " - Bed Details"),
                BorderLayout.NORTH
        );

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(215, 225, 240)
                        ),
                        BorderFactory.createEmptyBorder(25, 30, 25, 30)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField month = new JTextField(20);
        JTextField occupied = new JTextField(20);
        JTextField total = new JTextField(20);

        addField(form, gbc, 0, "Month:", month);
        addField(form, gbc, 1, "Beds Occupied:", occupied);
        addField(form, gbc, 2, "Total Beds:", total);

        mainPanel.add(form, BorderLayout.CENTER);

        JButton backButton = createButton("BACK", purpleColor);
        JButton saveButton = createButton("SAVE AND CONTINUE", greenColor);

        JPanel buttons = new JPanel();
        buttons.setBackground(backgroundColor);
        buttons.add(backButton);
        buttons.add(saveButton);

        mainPanel.add(buttons, BorderLayout.SOUTH);

        backButton.addActionListener(e -> showDepartment());

        saveButton.addActionListener(e -> {
            try {
                String monthValue = month.getText().trim();
                String occupiedText = occupied.getText().trim();
                String totalText = total.getText().trim();

                if (monthValue.isEmpty()
                        || occupiedText.isEmpty()
                        || totalText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Please fill in all bed details."
                    );
                    return;
                }

                int bedsOccupied = Integer.parseInt(occupiedText);
                int bedsTotal = Integer.parseInt(totalText);

                if (bedsOccupied < 0 || bedsTotal <= 0
                        || bedsOccupied > bedsTotal) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Enter valid bed counts. Occupied beds cannot exceed total beds."
                    );
                    return;
                }

                String sql = "INSERT INTO bed " +
                        "(month, bed_occupied, bed_total, department) " +
                        "VALUES (?, ?, ?, ?)";

                try (Connection con = getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setString(1, monthValue);
                    ps.setInt(2, bedsOccupied);
                    ps.setInt(3, bedsTotal);
                    ps.setString(4, selectedDepartment);

                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(
                        frame,
                        "Bed details saved successfully!"
                );

                demand();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Bed counts must be whole numbers."
                );
            } catch (SQLException ex) {
                showDatabaseError(ex);
            }
        });

        frame.setContentPane(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    // DEMAND DETAILS PAGE
    void demand() {
        frame.getContentPane().removeAll();

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );

        mainPanel.add(
                createTitle(selectedDepartment + " - Surgery Demand"),
                BorderLayout.NORTH
        );

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(215, 225, 240)
                        ),
                        BorderFactory.createEmptyBorder(20, 30, 20, 30)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField surgeryName = new JTextField(20);
        JTextField performedCount = new JTextField(20);
        JTextField unmetCount = new JTextField(20);
        JTextField equipmentCount = new JTextField(20);

        addField(form, gbc, 0, "Surgery Name:", surgeryName);
        addField(form, gbc, 1, "Performed Count:", performedCount);
        addField(form, gbc, 2, "Unmet Count:", unmetCount);
        addField(form, gbc, 3, "Equipment Count:", equipmentCount);

        mainPanel.add(form, BorderLayout.CENTER);

        JButton backButton = createButton("BACK", purpleColor);
        JButton saveButton = createButton("SAVE DEMAND", greenColor);
        JButton analysisButton = createButton("VIEW ANALYSIS", blueColor);

        JPanel buttons = new JPanel();
        buttons.setBackground(backgroundColor);
        buttons.add(backButton);
        buttons.add(saveButton);
        buttons.add(analysisButton);

        mainPanel.add(buttons, BorderLayout.SOUTH);

        backButton.addActionListener(e -> beds());

        saveButton.addActionListener(e -> {
            try {
                String surgery = surgeryName.getText().trim();
                String performedText = performedCount.getText().trim();
                String unmetText = unmetCount.getText().trim();
                String equipmentText = equipmentCount.getText().trim();

                if (surgery.isEmpty() || performedText.isEmpty()
                        || unmetText.isEmpty() || equipmentText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Please fill in all demand details."
                    );
                    return;
                }

                int performed = Integer.parseInt(performedText);
                int unmet = Integer.parseInt(unmetText);
                int equipment = Integer.parseInt(equipmentText);

                if (performed < 0 || unmet < 0 || equipment < 0) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Counts cannot be negative."
                    );
                    return;
                }

                String sql = "INSERT INTO demand " +
                        "(surgery_name, performed_count, unmet_count, " +
                        "equipment_count, department) VALUES (?, ?, ?, ?, ?)";

                try (Connection con = getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setString(1, surgery);
                    ps.setInt(2, performed);
                    ps.setInt(3, unmet);
                    ps.setInt(4, equipment);
                    ps.setString(5, selectedDepartment);

                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(
                        frame,
                        "Demand details saved successfully!"
                );

                surgeryName.setText("");
                performedCount.setText("");
                unmetCount.setText("");
                equipmentCount.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Counts must be whole numbers."
                );
            } catch (SQLException ex) {
                showDatabaseError(ex);
            }
        });

        analysisButton.addActionListener(e -> analysis());

        frame.setContentPane(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    // FINAL PAGE: ANALYSIS REPORT
    void analysis() {
        frame.getContentPane().removeAll();

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel title = createTitle(
                selectedDepartment + " - ANALYSIS REPORT"
        );
        mainPanel.add(title, BorderLayout.NORTH);

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        reportArea.setLineWrap(true);
        reportArea.setWrapStyleWord(true);
        reportArea.setMargin(new Insets(15, 15, 15, 15));
        reportArea.setBackground(Color.WHITE);
        reportArea.setForeground(new Color(35, 45, 60));

        try (Connection con = getConnection()) {
            HospitalReport hospitalReport =
                    new HospitalReport(con, selectedDepartment, 30);

            String reportText =
                    hospitalReport.generateReport()
                            + "\n\n"
                            + hospitalReport.showInference();

            reportArea.setText(reportText);
            reportArea.setCaretPosition(0);

        } catch (SQLException ex) {
            showDatabaseError(ex);
            return;
        }

        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(210, 220, 235)
                )
        );

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = createButton("BACK", purpleColor);
        JButton homeButton = createButton("HOME", purpleColor);
        JButton refreshButton = createButton("REFRESH ANALYSIS", blueColor);
        JButton saveButton = createButton("SAVE REPORT", greenColor);

        JPanel buttons = new JPanel();
        buttons.setBackground(backgroundColor);
        buttons.add(backButton);
        buttons.add(homeButton);
        buttons.add(refreshButton);
        buttons.add(saveButton);

        mainPanel.add(buttons, BorderLayout.SOUTH);

        // BACK returns to the demand page
        backButton.addActionListener(e -> demand());

        // HOME returns to the first page
        homeButton.addActionListener(e -> showHome());

        // REFRESH loads the latest database records
        refreshButton.addActionListener(e -> analysis());

        // SAVE REPORT writes the displayed report to a text file
        saveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File("HospitalReport.txt"));

            if (chooser.showSaveDialog(frame)
                    != JFileChooser.APPROVE_OPTION) {
                return;
            }

            try (PrintWriter writer =
                         new PrintWriter(chooser.getSelectedFile())) {

                writer.print(reportArea.getText());

                JOptionPane.showMessageDialog(
                        frame,
                        "Report saved successfully!",
                        "Saved",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Unable to save report: " + ex.getMessage(),
                        "Save Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        frame.setContentPane(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    // DATABASE ERROR MESSAGE
    void showDatabaseError(SQLException ex) {
        ex.printStackTrace();

        JOptionPane.showMessageDialog(
                frame,
                "Database error:\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // MAIN METHOD
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ignored) {
                // Continue with the default look and feel
            }

            new HospitalGUI();
        });
    }
}
