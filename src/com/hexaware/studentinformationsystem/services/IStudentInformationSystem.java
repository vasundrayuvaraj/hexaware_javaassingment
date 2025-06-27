package com.hexaware.studentinformationsystem.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hexaware.studentinformationsytem.model.Courses;
import com.hexaware.studentinformationsytem.model.Payments;
import com.hexaware.studentinformationsytem.model.Students;
import com.hexaware.studentinformationsytem.model.Teacher;


public interface IStudentInformationSystem {
        
    boolean enrollStudentInCourse(int studentID, int courseID, Date enrollmentDate);
  
    boolean assignTeacherToCourse(int teacherID, int courseID);
  
    
    List<Students> generateEnrollmentReport(int courseId);
    
    List<Payments> generatePaymentReport(int studentId);
    boolean recordPayment(int studentId, BigDecimal amount, Date paymentDate);
    
    
}