package org.example;

import javax.swing.*;
import java.awt.*;

public class MasterScreen extends JFrame {

    public MasterScreen() {
        // Window Settings
        setTitle("AI Face Attendance System - Main Menu");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel - Thoda light blue professional look
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        JLabel title = new JLabel("ATTENDANCE MANAGEMENT SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(3, 1, 25, 25));
        btnPanel.setBackground(new Color(44, 62, 80)); // Dark Blue-Grey background
        btnPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JButton btnCamera = new JButton("📸 OPEN ATTENDANCE CAMERA");
        JButton btnTeacher = new JButton("👨‍🏫 TEACHER DASHBOARD");
        JButton btnStudent = new JButton("🎓 STUDENT PORTAL");

        // Styling Buttons - Light & Vibrant Shades
        styleButton(btnCamera, new Color(46, 204, 113)); // Vibrant Light Green
        styleButton(btnTeacher, new Color(52, 152, 219)); // Vibrant Light Blue
        styleButton(btnStudent, new Color(155, 89, 182)); // Vibrant Light Purple

        // --- BUTTON ACTIONS ---

        // 1. Camera Action
        btnCamera.addActionListener(e -> {
            try {
                new Main().main(new String[]{});
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Camera Error: " + ex.getMessage());
            }
        });

        // 2. Teacher Login Action (ID: admin | Pass: teacher123)
        btnTeacher.addActionListener(e -> {
            JPanel loginPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            JTextField idField = new JTextField();
            JPasswordField passField = new JPasswordField();

            loginPanel.add(new JLabel("Teacher ID:"));
            loginPanel.add(idField);
            loginPanel.add(new JLabel("Password:"));
            loginPanel.add(passField);

            int result = JOptionPane.showConfirmDialog(this, loginPanel,
                    "Teacher Login", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String id = idField.getText();
                String password = new String(passField.getPassword());

                if (id.equals("admin") && password.equals("teacher123")) {
                    new TeacherDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong ID/Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 3. Student Action
        btnStudent.addActionListener(e -> new StudentDashboard());

        btnPanel.add(btnCamera);
        btnPanel.add(btnTeacher);
        btnPanel.add(btnStudent);

        add(btnPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel("Developed by K.r - B.Sc. CS 4th Sem", JLabel.CENTER);
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setFont(new Font("Arial", Font.ITALIC, 12));
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));

        // Solid appearance fix
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);

        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(color.brighter(), 2)); // Light border for pop
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        new MasterScreen();
    }
}