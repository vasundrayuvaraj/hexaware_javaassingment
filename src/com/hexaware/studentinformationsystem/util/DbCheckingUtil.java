package com.hexaware.studentinformationsystem.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbCheckingUtil {
	 public static boolean isStudentExists(int studentId) throws SQLException {
		    Connection connection = DBConnUtil.getConnection() ;
		         String sql = "SELECT * FROM Students WHERE student_id = ?";
		        try (PreparedStatement pst=connection.prepareStatement(sql)) {
		            pst.setInt(1,studentId);
		            try (ResultSet resultSet = pst.executeQuery()) {
		                return resultSet.next();
		            }
		        }
		    }
	 
	 public boolean isCourseExists(int courseId) throws SQLException {
	        Connection connection = DBConnUtil.getConnection() ;
	        String sql = "SELECT * FROM Courses WHERE course_id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setInt(1, courseId);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                return resultSet.next();
	            }
	        }
	    }
}
