package com.hexaware.studentinformationsystem.dao;
import com.hexaware.studentinformationsystem.services.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hexaware.studentinformationsystem.exceptions.CourseNotFoundException;
import com.hexaware.studentinformationsystem.exceptions.StudentNotFoundException;
import com.hexaware.studentinformationsystem.util.DBConnUtil;
import com.hexaware.studentinformationsytem.model.Courses;
import com.hexaware.studentinformationsytem.model.Enrollments;
import com.hexaware.studentinformationsytem.model.Students;

public class EnrollmentDao {

	private Connection connection = DBConnUtil.getConnection();

	public Students getStudent(Enrollments enrollment) {
		try {
			// Retrieve the student associated with the enrollment
			String sql = "SELECT * FROM Students WHERE student_id = ?";
			try (PreparedStatement pst = connection.prepareStatement(sql)) {
				pst.setInt(1, enrollment.getStudentId());

				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						int studentId = rs.getInt("student_id");
						String firstName = rs.getString("first_name");
						String lastName = rs.getString("last_name");
						Date dateOfBirth = rs.getDate("dateOfBirth");
						String email = rs.getString("email");
						String phoneNumber = rs.getString("phoneNumber");
						return new Students(studentId, firstName, lastName, dateOfBirth, email, phoneNumber);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return null;
	}

	public Courses getCourse(Enrollments enrollment) {
	    try {
	        String sql = "SELECT * FROM Courses WHERE course_id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setInt(1, enrollment.getCourseId());

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    int courseId = resultSet.getInt("course_id");
	                    String courseName = resultSet.getString("course_name");
	                    return new Courses(courseId, courseName); // this is the correct place
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving course: " + e.getMessage());
	    }

	    return null; // return null if not found or error occurred
	}


	  public List<Students> fetchEnrolledStudentsByCourseId(int courseId, Connection connection) throws SQLException {
	        List<Students> students = new ArrayList<>();

	        String sql = "SELECT s.student_id, s.first_name, s.last_name, s.date_of_birth, s.email, s.phone_number " +
	                     "FROM Students s " +
	                     "JOIN Enrollments e ON s.student_id = e.student_id " +
	                     "WHERE e.course_id = ?";

	        try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setInt(1, courseId);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Students student = new Students(
	                        rs.getInt("student_id"),
	                        rs.getString("first_name"),
	                        rs.getString("last_name"),
	                        rs.getDate("date_of_birth"),
	                        rs.getString("email"),
	                        rs.getString("phone_number")
	                    );
	                    students.add(student);
	                }
	            }
	        }

	        return students;
	    }

	  public boolean isStudentExists(int studentId, Connection connection) {
	        String sql = "SELECT * FROM Students WHERE student_id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setInt(1, studentId);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                return resultSet.next();
	            }
	        } catch (SQLException e) {
	            System.err.println("Error checking if student exists: " + e.getMessage());
	            return false;
	        }
	    }

	    public boolean isCourseExists(int courseId, Connection connection) {
	        String sql = "SELECT * FROM Courses WHERE course_id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setInt(1, courseId);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                return resultSet.next();
	            }
	        } catch (SQLException e) {
	            System.err.println("Error checking if course exists: " + e.getMessage());
	            return false;
	        }
	    }

	    public boolean isEnrollmentExists(int studentId, int courseId, Connection connection) {
	        String sql = "SELECT * FROM Enrollments WHERE student_id = ? AND course_id = ?";
	        try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setInt(1, studentId);
	            ps.setInt(2, courseId);
	            try (ResultSet rs = ps.executeQuery()) {
	                return rs.next();
	            }
	        } catch (SQLException e) {
	            System.err.println("Error checking if enrollment exists: " + e.getMessage());
	            return false;
	        }
	    }

	    public void insertEnrollment(int studentId, int courseId, java.sql.Date enrollmentDate, Connection connection) {
	        String sql = "INSERT INTO Enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";
	        try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setInt(1, studentId);
	            ps.setInt(2, courseId);
	            ps.setDate(3, enrollmentDate);
	            ps.executeUpdate();
	            System.out.println("Enrollment inserted successfully.");
	        } catch (SQLException e) {
	            System.err.println("Error inserting enrollment: " + e.getMessage());
	        }

	
	    }
}
