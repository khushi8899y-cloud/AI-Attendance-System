package org.example;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import java.sql.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_objdetect.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // 1. Database Connection Info
        String url = "jdbc:mysql://localhost:3306/attendance_db";
        String user = "root";
        String password = "khushi12"; // <--- Put your CMD password here!

        // 2. Load the Face Detection "Manual" (The XML file you pasted)
        CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_default.xml");

        // 3. Start Camera
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();
        CanvasFrame canvas = new CanvasFrame("AI Attendance System");
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();

        System.out.println("Camera is LIVE. Looking for faces...");

        boolean attendanceMarked = false;

        while (canvas.isVisible()) {
            Frame frame = grabber.grab();
            Mat image = converter.convert(frame);

            if (image != null) {
                // 4. Detection Logic
                RectVector faces = new RectVector();
                faceDetector.detectMultiScale(image, faces);

                // 5. Draw Green Box and Mark Database
                for (long i = 0; i < faces.size(); i++) {
                    Rect face = faces.get(i);

                    // Draw the rectangle
                    rectangle(image, new Point(face.x(), face.y()),
                            new Point(face.x() + face.width(), face.y() + face.height()),
                            Scalar.GREEN, 3, LINE_8, 0);

                    // If a face is found and we haven't marked attendance yet, save to MySQL
                    if (!attendanceMarked) {
                        try (Connection conn = DriverManager.getConnection(url, user, password)) {
                            String sql = "INSERT INTO students (name, status) VALUES (?, ?)";
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, "Khushi (Detected)");
                            pstmt.setString(2, "Present");
                            pstmt.executeUpdate();
                            System.out.println(">>> SUCCESS: Face detected and Attendance marked!");
                            attendanceMarked = true; // Prevents marking 1000 times a second
                        } catch (SQLException e) {
                            System.out.println("DB Error: " + e.getMessage());
                        }
                    }
                }
                canvas.showImage(converter.convert(image));
            }
        }
        grabber.stop();
        canvas.dispose();
    }
}