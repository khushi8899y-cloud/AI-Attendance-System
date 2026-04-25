package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class TeacherDashboard extends JFrame {
    JTable table;
    DefaultTableModel model;
    JButton btnAlert;

    public TeacherDashboard() {
        setTitle("Teacher Dashboard - Attendance Alerts");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Attendance Monitor (< 75%)", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        add(header, BorderLayout.NORTH);

        String[] columns = {"Name", "Email", "Classes", "Percentage"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAlert = new JButton("Send WhatsApp/Email Alert");
        btnAlert.setBackground(new Color(220, 53, 69));
        btnAlert.setForeground(Color.WHITE);
        add(btnAlert, BorderLayout.SOUTH);

        // Button Click Logic
        btnAlert.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String name = model.getValueAt(row, 0).toString();
                String email = model.getValueAt(row, 1).toString();
                String percent = model.getValueAt(row, 3).toString();
                sendEmailAlert(email, name, percent);
            } else {
                JOptionPane.showMessageDialog(this, "Select Student!");
            }
        });

        fetchData();
        setVisible(true);
    }

    private void fetchData() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance_db", "root", "khushi12")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name, MAX(email) as email, COUNT(*) as classes, (COUNT(*)/50.0)*100 as percentage FROM students GROUP BY name HAVING percentage < 75");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("name"), rs.getString("email"), rs.getInt("classes"), rs.getDouble("percentage") + "%"});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void sendEmailAlert(String toEmail, String studentName, String percent) {
        final String fromEmail = "khushi8899y@gmail.com"; // Apna Gmail yahan likhein
        final String appPassword = "ifut mkor xpyn nirc"; // 16-digit App Password yahan likhein

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Attendance Alert: " + studentName);
            message.setText("Dear " + studentName + ",\n\nYour attendance is " + percent + ". It is below 75%. Please attend classes regularly to avoid any issues.");

            Transport.send(message);
            JOptionPane.showMessageDialog(this, "Alert sent successfully to " + studentName);
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(this, "Email error: " + e.getMessage());
        }
    }

    public static void main(String[] args) { new TeacherDashboard(); }
}