package com.hexaware.studentinformationsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.studentinformationsystem.exceptions.CourseNotFoundException;
import com.hexaware.studentinformationsystem.exceptions.TeacherNotFoundException;
import com.hexaware.studentinformationsystem.util.DBConnUtil;
import com.hexaware.studentinformationsytem.model.Courses;
import com.hexaware.studentinformationsytem.model.Enrollments;
import com.hexaware.studentinformationsytem.model.Students;
import com.hexaware.studentinformationsytem.model.Teacher;

public class CourseDao {
	
	 private Connection connection = DBConnUtil.getConnection()  ; 

	    

	    public void assignTeacherToCourse(Courses course, Teacher teacher) {
	        try {
	            // Check if the course and teacher exist
	            if (!isCourseExists(course.getCourseId())) {
	                throw new CourseNotFoundException("Course not found with ID: " + course.getCourseId());
	            }

	            if (!isTeacherExists(teacher.getTeacherId())) {
	                throw new TeacherNotFoundException("Teacher not found with ID: " + teacher.getTeacherId());
	            }

	            // Assign the teacher to the course
	            String sql = "UPDATE Courses SET teacher_id = ? WHERE course_id = ?";
	            try (PreparedStatement pst = connection.prepareStatement(sql)) {
	                pst.setInt(1, teacher.getTeacherId());
	                pst.setInt(2, course.getCourseId());

	                pst.executeUpdate();
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	    
	    public void updateCourseInfo(String courseCode, String courseName, String instructor) {
	        try {
	            // Update course information
	            String sql = "UPDATE Courses SET course_name = ?, instructor = ? WHERE course_code = ?";
	            try (PreparedStatement pst = connection.prepareStatement(sql)) {
	                pst.setString(1, courseName);
	                pst.setString(2, instructor);
	                pst.setString(3, courseCode);

	                pst.executeUpdate();
	            }
	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	
	    public Courses getCourseInfo(int courseId) {
	        Courses course = null;

	        try (Connection connection = DBConnUtil.getConnection()) {
	            String sql = "SELECT * FROM Courses WHERE course_id = ?";
	            try (PreparedStatement pst = connection.prepareStatement(sql)) {
	                pst.setInt(1, courseId);

	                try (ResultSet resultSet = pst.executeQuery()) {
	                    if (resultSet.next()) {
	                        String courseName = resultSet.getString("course_name");
	                        int credits = resultSet.getInt("credits");
	                        int teacherId = resultSet.getInt("teacher_id"); // ✅ use int for foreign key

	                        course = new Courses(courseId, courseName, credits, teacherId);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }

	        return course;
	    }


	    public List<Enrollments> getEnrollments(Courses course) {
	        List<Enrollments> enrollments = new ArrayList<>();

	        try {
	            // Retrieve student enrollments for the course
	            String sql = "SELECT * FROM Enrollments WHERE course_id = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setInt(1, course.getCourseId());

	                try (ResultSet rs = preparedStatement.executeQuery()) {
	                    while (rs.next()) {
	                        int studentId = rs.getInt("student_id");
	           
	                        enrollments.add(new Enrollments(studentId, course.getCourseId()));
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }

	        return enrollments;
	    }

	    public Teacher getTeacher(Courses course) {
	        try {
	            // Retrieve the assigned teacher for the course
	            String sql = "SELECT * FROM Teachers WHERE teacher_id = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            	preparedStatement.setInt(1, course.getTeacherId());


	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    if (resultSet.next()) {
	                        int teacherId = resultSet.getInt("teacher_id");
	                        return new Teacher(teacherId);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error: " + e.getMessage());
	        }

	        return null;
	    }

	    public List<Students> getEnrolledStudents(int courseId, Connection connection) throws SQLException {
	        List<Students> students = new ArrayList<>();

	        String sql = "SELECT s.student_id, s.first_name, s.last_name, s.date_of_birth, s.email, s.phone_number " +
	                     "FROM Students s " +
	                     "JOIN Enrollments e ON s.student_id = e.student_id " +
	                     "WHERE e.course_id = ?";

	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, courseId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    Students student = new Students();
	                    student.setStudentId(rs.getInt("student_id"));
	                    student.setFirstName(rs.getString("first_name"));
	                    student.setLastName(rs.getString("last_name"));
	                    student.setDob(rs.getDate("date_of_birth"));
	                    student.setEmail(rs.getString("email"));
	                    student.setPhone(rs.getString("phone_number"));
	                    students.add(student);
	                }
	            }
	        }

	        return students;
	    }

	    private boolean isCourseExists(int courseId) {
	        String sql = "SELECT * FROM Courses WHERE course_id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setInt(1, courseId);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                return resultSet.next();
	            }
	        } catch (SQLException e) {
	            System.err.println("Error in isCourseExists: " + e.getMessage());
	            return false; // If exception occurs, return false (i.e., course not found)
	        }
	    }

	    private boolean isTeacherExists(int teacherId) {
	        String sql = "SELECT * FROM Teacher WHERE teacher_id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setInt(1, teacherId);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                return resultSet.next();
	            }
	        } catch (SQLException e) {
	            System.err.println("Error in isTeacherExists: " + e.getMessage());
	            return false;
	        }
	    }

	    
	    public boolean isCourseExists(int courseId, Connection connection) {
	        String sql = "SELECT 1 FROM Courses WHERE course_id = ?";
	        try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setInt(1, courseId);
	            try (ResultSet rs = ps.executeQuery()) {
	                return rs.next(); // true if course exists
	            }
	        } catch (SQLException e) {
	            System.err.println("Error checking course existence: " + e.getMessage());
	            return false; // assume not found if error occurs
	        }
	    }


	    public void assignTeacherToCourse(int courseId, int teacherId, Connection connection) {
	        String sql = "UPDATE Courses SET teacher_id = ? WHERE course_id = ?";
	        try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setInt(1, teacherId);
	            ps.setInt(2, courseId);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            System.err.println("Error in assignTeacherToCourse: " + e.getMessage());
	        }
	    }

}
