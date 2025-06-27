package com.hexaware.studentinformationsystem.services;

import com.hexaware.studentinformationsystem.services.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.studentinformationsystem.dao.*;
import com.hexaware.studentinformationsystem.exceptions.*;
import com.hexaware.studentinformationsystem.util.DBConnUtil;
import com.hexaware.studentinformationsytem.model.*;

public class StudentInformationSystemImpl implements IStudentInformationSystem {

	private final StudentDao studentDao = new StudentDao();
	private final CourseDao courseDao = new CourseDao();
	private final EnrollmentDao enrollmentDao = new EnrollmentDao();
	private final PaymentDao paymentDao = new PaymentDao();
	private final TeacherDao teacherDao = new TeacherDao();

	@Override
	public boolean enrollStudentInCourse(int studentID, int courseId, java.util.Date enrollmentDate) {
		try (Connection connection = DBConnUtil.getConnection()) {
			if (!studentDao.isStudentExists(studentID)) {
			    throw new StudentNotFoundException("Student not found with ID: " + studentID);
			}


			if (!courseDao.isCourseExists(courseId, connection)) {
				throw new CourseNotFoundException("Course not found with ID: " + courseId);
			}

			if (enrollmentDao.isEnrollmentExists(studentID, courseId, connection)) {
				throw new DuplicateEnrollmentException("Student is already enrolled in the course.");
			}

			enrollmentDao.insertEnrollment(studentID, courseId, new Date(enrollmentDate.getTime()), connection);
			return true;

		} catch (Exception e) {
			System.out.println( e.getMessage());
			return false;
		}
	}

	@Override
	public boolean assignTeacherToCourse(int teacherId, int courseId) {
		try (Connection connection = DBConnUtil.getConnection()) {
			if (!teacherDao.isTeacherExists(teacherId, connection)) {
				throw new TeacherNotFoundException("Teacher not found with ID: " + teacherId);
			}

			if (!courseDao.isCourseExists(courseId, connection)) {
				throw new CourseNotFoundException("Course not found with ID: " + courseId);
			}

			courseDao.assignTeacherToCourse(courseId, teacherId, connection);
			return true;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean recordPayment(int studentId, BigDecimal amount, java.util.Date paymentDate) {
	    try (Connection connection = DBConnUtil.getConnection()) {

	        if (!isStudentExists(studentId, connection)) {
	            throw new StudentNotFoundException("Student not found with ID: " + studentId);
	        }

	        // ✅ Convert util.Date to sql.Date
	        java.sql.Date sqlPaymentDate = new java.sql.Date(paymentDate.getTime());

	        paymentDao.insertPayment(studentId, amount, sqlPaymentDate, connection);

	        System.out.println("Payment recorded successfully.");
	        return true;

	    } catch (StudentNotFoundException e) {
	        System.out.println("Error: " + e.getMessage());
	        return false;

	    } catch (Exception e) {
	        System.out.println("Unexpected error: " + e.getMessage());
	        return false;
	    }
	}



	public List<Students> generateEnrollmentReport(int courseId) {
	    try (Connection connection = DBConnUtil.getConnection()) {
	        if (!courseDao.isCourseExists(courseId, connection)) {
	            System.out.println("Error: Course not found with ID: " + courseId);
	            return null;  // Important to return null to indicate course not found
	        }

	        return enrollmentDao.fetchEnrolledStudentsByCourseId(courseId, connection);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}



	@Override
	public List<Payments> generatePaymentReport(int studentId) {
		List<Payments> payments = new ArrayList<>();

		try (Connection connection = DBConnUtil.getConnection()) {
			if (!studentDao.isStudentExists(studentId)) {
				throw new StudentNotFoundException("Student not found with ID: " + studentId);
			}

			Students student = studentDao.getStudentById(studentId);
			payments = paymentDao.getPaymentsByStudentId(studentId, connection, student);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return payments;
	}

	private boolean isStudentExists(int studentId, Connection connection) throws SQLException {
		String sql = "SELECT * FROM Students WHERE student_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, studentId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next(); // returns true if a row exists
			}
		}
	}

	private boolean isTeacherExists(int teacherId, Connection connection) throws SQLException {
		String sql = "SELECT * FROM teacher WHERE teacher_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, teacherId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next(); // true if teacher exists
			}
		}
	}

}
