package com.hexaware.studentinformationsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.studentinformationsystem.util.DBConnUtil;
import com.hexaware.studentinformationsytem.model.Courses;
import com.hexaware.studentinformationsytem.model.Teacher;

public class TeacherDao {

    private Connection connection = DBConnUtil.getConnection();

    // Method to update teacher details
    public void updateTeacherInfo(Teacher teacher) {
        String sql = "UPDATE Teacher SET first_name = ?, email = ?, last_name = ? WHERE teacher_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getEmail());
            preparedStatement.setString(3, teacher.getLastName());
            preparedStatement.setInt(4, teacher.getTeacherId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in updateTeacherInfo: " + e.getMessage());
        }
    }

    // Method to display teacher details
    public Teacher displayTeacherInfo(int teacherId) {
        String sql = "SELECT * FROM Teacher WHERE teacher_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, teacherId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name"); 
                    String email = resultSet.getString("email");

                    return new Teacher(teacherId, firstName, lastName, email);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in displayTeacherInfo: " + e.getMessage());
        }
        return null;
    }

    // Method to fetch courses assigned to a teacher
    public List<Courses> getAssignedCourses(int teacherId) {
        List<Courses> assignedCourses = new ArrayList<>();
        String sql = "SELECT * FROM Courses WHERE teacher_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, teacherId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int courseId = resultSet.getInt("course_id");
                    String courseName = resultSet.getString("course_name");

                    assignedCourses.add(new Courses(courseId, courseName));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in getAssignedCourses: " + e.getMessage());
        }

        return assignedCourses;
    }

    // Method to check if teacher exists using given connection
    public boolean isTeacherExists(int teacherId, Connection connection2) {
        String sql = "SELECT * FROM Teacher WHERE teacher_id = ?";
        try (PreparedStatement preparedStatement = connection2.prepareStatement(sql)) {
            preparedStatement.setInt(1, teacherId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error in isTeacherExists: " + e.getMessage());
            return false;
        }
    }
}
