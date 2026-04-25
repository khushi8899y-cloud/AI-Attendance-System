package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentDashboard extends JFrame {
    JTextField nameField;
    JLabel statusLabel, percentLabel;
    JButton checkBtn;

    public StudentDashboard() {
        setTitle("Student Attendance Portal");
        setSize(400, 300);
        setLayout(new GridLayout(5, 1, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel("Enter Your Name:", JLabel.CENTER));
        nameField = new JTextField();
        add(nameField);

        checkBtn = new JButton("Check My Status");
        add(checkBtn);

        percentLabel = new JLabel("Percentage: --%", JLabel.CENTER);
        percentLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(percentLabel);

        statusLabel = new JLabel("", JLabel.CENTER);
        add(statusLabel);

        checkBtn.addActionListener(e -> fetchStatus());

        setVisible(true);
    }

    private void fetchStatus() {
        String studentName = nameField.getText();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance_db", "root", "khushi12")) {
            // Hum direct query use karenge percentage nikalne ke liye
            String query = "SELECT (COUNT(*) / 50.0) * 100 as percentage FROM students WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, studentName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double p = rs.getDouble("percentage");
                percentLabel.setText("Percentage: " + p + "%");

                if (p >= 75) {
                    statusLabel.setText("Status: SAFE ✅");
                    statusLabel.setForeground(new Color(34, 139, 34));
                } else {
                    statusLabel.setText("Status: SHORT ATTENDANCE ⚠️");
                    statusLabel.setForeground(Color.RED);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) { new StudentDashboard(); }
}