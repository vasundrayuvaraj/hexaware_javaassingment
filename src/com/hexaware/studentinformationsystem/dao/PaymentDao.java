package com.hexaware.studentinformationsystem.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hexaware.studentinformationsystem.exceptions.StudentNotFoundException;
import com.hexaware.studentinformationsystem.util.DBConnUtil;
import com.hexaware.studentinformationsytem.model.Payments;
import com.hexaware.studentinformationsytem.model.Students;

public class PaymentDao {

 private Connection con = DBConnUtil.getConnection()  ; 

    public Students getStudent(int paymentId) {
        try {
            // Retrieve student associated with the payment
            String sql = "SELECT Students.* FROM Students " +
                    "INNER JOIN Payments ON Students.student_id = Payments.student_id " +
                    "WHERE Payments.payment_id = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, paymentId);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        int studentId = rs.getInt("student_id");
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        Date dateOfBirth = rs.getDate("date_of_birth");
                        String email = rs.getString("email");
                        String phoneNumber = rs.getString("phone_number");
                       
                        return new Students(studentId, firstName, lastName, dateOfBirth, email, phoneNumber);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    public BigDecimal getPaymentAmount(int paymentId) {
        try {
            // Retrieve payment amount
            String sql = "SELECT amount FROM Payments WHERE payment_id = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, paymentId);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBigDecimal("amount");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    public Date getPaymentDate(int paymentId) {
        try {
            // Retrieve payment date
            String sql = "SELECT payment_date FROM Payments WHERE payment_id = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, paymentId);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return rs.getDate("payment_date");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    private boolean isStudentExists(int studentId) {
        String sql = "SELECT * FROM Students WHERE student_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet resultSet = ps.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error in isStudentExists: " + e.getMessage());
            return false;
        }
    }

    public void insertPayment(int studentId, BigDecimal amount, java.sql.Date paymentDate, Connection connection) {
        String sql = "INSERT INTO Payments (student_id, amount, payment_date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setBigDecimal(2, amount);
            ps.setDate(3, paymentDate);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in insertPayment: " + e.getMessage());
        }
    }

    public List<Payments> getPaymentsByStudentId(int studentId, Connection connection, Students student) {
        List<Payments> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int paymentId = rs.getInt("payment_id");
                    BigDecimal amount = rs.getBigDecimal("amount");
                    Date paymentDate = rs.getDate("payment_date");
                    payments.add(new Payments(student, paymentId, paymentDate, amount));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in getPaymentsByStudentId: " + e.getMessage());
        }
        return payments;
    }

}
