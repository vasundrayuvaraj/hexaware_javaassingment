package com.hexaware.studentinformationsystem.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hexaware.studentinformationsystem.exceptions.CourseNotFoundException;
import com.hexaware.studentinformationsystem.exceptions.DuplicateEnrollmentException;
import com.hexaware.studentinformationsystem.exceptions.PaymentValidationException;
import com.hexaware.studentinformationsystem.exceptions.StudentNotFoundException;
import com.hexaware.studentinformationsystem.util.DBConnUtil;
import com.hexaware.studentinformationsytem.model.Courses;
import com.hexaware.studentinformationsytem.model.Payments;
import com.hexaware.studentinformationsytem.model.Students;

public class StudentDao {

    private Connection connection = DBConnUtil.getConnection();

    public void updateStudentInfo(Students student, String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) {
        try {
            if (!isStudentExists(student.getStudentId())) {
                throw new StudentNotFoundException("Student not found with ID: " + student.getStudentId());
            }

            String sql = "UPDATE Students SET first_name = ?, last_name = ?, date_of_birth = ?, email = ?, phone_number = ? WHERE student_id = ?";
            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                pst.setString(1, firstName);
                pst.setString(2, lastName);
                pst.setDate(3, new java.sql.Date(dateOfBirth.getTime()));
                pst.setString(4, email);
                pst.setString(5, phoneNumber);
                pst.setInt(6, student.getStudentId());
                pst.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Error in updateStudentInfo: " + e.getMessage());
        }
    }

    public void makePayment(Students student, BigDecimal amount, Date paymentDate) {
        try {
            if (!isStudentExists(student.getStudentId())) {
                throw new StudentNotFoundException("Student not found with ID: " + student.getStudentId());
            }

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new PaymentValidationException("Invalid payment amount.");
            }

            String sql = "INSERT INTO Payments (student_id, amount, payment_date) VALUES (?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                pst.setInt(1, student.getStudentId());
                pst.setBigDecimal(2, amount);
                pst.setDate(3, new java.sql.Date(paymentDate.getTime()));
                pst.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Error in makePayment: " + e.getMessage());
        }
    }

    public Students getStudentInfoById(int studentId) {
        try {
            String sql = "SELECT * FROM Students WHERE student_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, studentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        Date dob = resultSet.getDate("date_of_birth");
                        String email = resultSet.getString("email");
                        String phone = resultSet.getString("phone_number");

                        return new Students(studentId, firstName, lastName, dob, email, phone);
                    } else {
                        throw new StudentNotFoundException("Student not found with ID: " + studentId);
                    }
                }
            }
        } catch (StudentNotFoundException | SQLException e) {
            System.err.println("Error in getStudentInfoById: " + e.getMessage());
            return null;
        }
    }

    public List<Courses> getEnrolledCourses(Students student) {
        List<Courses> enrolledCourses = new ArrayList<>();

        try {
            if (!isStudentExists(student.getStudentId())) {
                throw new StudentNotFoundException("Student not found with ID: " + student.getStudentId());
            }

            String sql = "SELECT Courses.* FROM Courses " +
                         "INNER JOIN Enrollments ON Courses.course_id = Enrollments.course_id " +
                         "WHERE Enrollments.student_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, student.getStudentId());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int courseId = resultSet.getInt("course_id");
                        String courseName = resultSet.getString("course_name");
                        enrolledCourses.add(new Courses(courseId, courseName));
                    }
                }
            }
        } catch (StudentNotFoundException | SQLException e) {
            System.err.println("Error in getEnrolledCourses: " + e.getMessage());
        }

        return enrolledCourses;
    }

    public List<Payments> getPaymentHistory(Students student) {
        List<Payments> paymentHistory = new ArrayList<>();

        try {
            if (!isStudentExists(student.getStudentId())) {
                throw new StudentNotFoundException("Student not found with ID: " + student.getStudentId());
            }

            String sql = "SELECT * FROM Payments WHERE student_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, student.getStudentId());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int paymentId = resultSet.getInt("payment_id");
                        BigDecimal amount = resultSet.getBigDecimal("amount");
                        Date paymentDate = resultSet.getDate("payment_date");

                        paymentHistory.add(new Payments(student, paymentId, paymentDate, amount));
                    }
                }
            }
        } catch (StudentNotFoundException | SQLException e) {
            System.err.println("Error in getPaymentHistory: " + e.getMessage());
        }

        return paymentHistory;
    }

    public Students getStudentById(int studentId) {
        try {
            if (!isStudentExists(studentId)) {
                throw new StudentNotFoundException("Student not found with ID: " + studentId);
            }

            String sql = "SELECT * FROM Students WHERE student_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, studentId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        Date dob = resultSet.getDate("date_of_birth");
                        String email = resultSet.getString("email");
                        String phone = resultSet.getString("phone_number");

                        return new Students(studentId, firstName, lastName, dob, email, phone);
                    }
                }
            }
        } catch (StudentNotFoundException | SQLException e) {
            System.err.println("Error in getStudentById: " + e.getMessage());
        }

        return null;
    }

    public boolean isStudentExists(int studentId) {
        String sql = "SELECT * FROM Students WHERE student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error in isStudentExists: " + e.getMessage());
            return false;
        }
    }

    private boolean isEnrollmentExists(int studentId, int courseId) {
        String sql = "SELECT * FROM Enrollments WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error in isEnrollmentExists: " + e.getMessage());
            return false;
        }
    }
}
