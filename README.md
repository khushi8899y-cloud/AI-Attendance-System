# AI-Powered Face Attendance System 📸🎓

An automated attendance management application developed using **Java**, **MySQL**, and **OpenCV**. This project replaces traditional manual registers with real-time facial recognition and automated communication.

## ✨ Key Features
- **Facial Recognition:** Detects and marks attendance using Haar-Cascade classifiers.
- **Teacher Dashboard:** Secure login to monitor student attendance analytics.
- **Automated SMTP Alerts:** Sends email notifications to students with attendance below 75%.
- **Student Portal:** Allows students to check their individual attendance status.

## 🛠️ Tech Stack
- **Frontend:** Java Swing, AWT (Unit-IV Syllabus)
- **Backend:** MySQL RDBMS (Unit-I to V Syllabus)
- **Connectivity:** JDBC (Java Database Connectivity)
- **AI Engine:** OpenCV
- **Communication:** JavaMail API (SMTP)

## 📂 Project Structure
- `src/`: Contains the core Java logic and GUI frames.
- `haarcascade_frontalface_default.xml`: Pre-trained model for face detection.
- `pom.xml`: Project dependencies and configuration.

## 🚀 How to Run
1. Setup a MySQL database named `attendance_db`.
2. Configure your Gmail SMTP credentials in the `TeacherDashboard.java` file.
3. Run `MasterScreen.java` as the entry point of the application.

---
*Developed by Khushi - Computer Science*
