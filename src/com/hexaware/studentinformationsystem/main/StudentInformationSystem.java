

package com.hexaware.studentinformationsystem.main;
import com.hexaware.studentinformationsystem.exceptions.*;
import java.math.BigDecimal;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import com.hexaware.studentinformationsystem.services.StudentInformationSystemImpl;
import com.hexaware.studentinformationsystem.dao.CourseDao;
import com.hexaware.studentinformationsystem.dao.StudentDao;
import com.hexaware.studentinformationsytem.model.*;
import com.hexaware.studentinformationsystem.exceptions.*;

import java.text.SimpleDateFormat;
import java.util.List;

public class StudentInformationSystem {

     private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        StudentInformationSystemImpl sisImplementation = new StudentInformationSystemImpl();

        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Update Student Information");
            System.out.println("2. Enroll Student in a Course");
            System.out.println("3. Assign Teacher to a Course");
            System.out.println("4. Record Payment");
            System.out.println("5. Generate Enrollment Report");
            System.out.println("6. Generate Payment Report");
            System.out.println("7. Exit");
            System.out.println("8. View Course Information");
            System.out.println("9. View Student Information");



            System.out.print("Enter your choice (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    updateStudentInformation(sisImplementation);
                    break;
                case 2:
                    enrollStudentInCourse(sisImplementation);
                    break;
                case 3:
                    assignTeacherToCourse(sisImplementation);
                    break;
                case 4:
                    recordPayment(sisImplementation);
                    break;
                case 5:
                    generateEnrollmentReport(sisImplementation);
                    break;
                case 6:
                    generatePaymentReport(sisImplementation);
                    break;
                case 7:
                    System.out.println("Exiting the program.");
                    return;
                case 8:
                    displayCourseInformation();
                    break;
                case 9:
                    displayStudentInformation();
                    break;

     
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 7.");
            }
        }
    }

    private static void displayStudentInformation() {
		// TODO Auto-generated method stub
    	
    	try {
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();

            StudentDao studentDao = new StudentDao();
            Students student = studentDao.getStudentInfoById(studentId);

            System.out.println("Student ID: " + student.getStudentId());
            System.out.println("First Name: " + student.getFirstName());
            System.out.println("Last Name: " + student.getLastName());
            System.out.println("Date of Birth: " + student.getDob());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Phone Number: " + student.getPhone());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
		
	}

	private static void displayCourseInformation() {
		// TODO Auto-generated method stub
    	 System.out.print("Enter Course ID: ");
    	    int courseId = scanner.nextInt();
    	    scanner.nextLine();

    	    CourseDao courseDao = new CourseDao();
    	    Courses course = courseDao.getCourseInfo(courseId);

    	    if (course != null) {
    	        System.out.println("Course ID: " + course.getCourseId());
    	        System.out.println("Course Name: " + course.getCourseName());
    	        System.out.println("credits:"+course.getCredits());
    	        System.out.println("Instructor: " + course.getTeacherId());
    	    } else {
    	        System.out.println("Course not found.");
    	    }
		
	}

	private static void updateStudentInformation(StudentInformationSystemImpl sisImplementation) {
        try {
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            System.out.print("Enter new first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter new last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter new date of birth (yyyy-MM-dd): ");
            Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());


            System.out.print("Enter new email: ");
            String email = scanner.nextLine();

            System.out.print("Enter new phone number: ");
            String phoneNumber = scanner.nextLine();

            
            Students student= new Students(studentId, firstName, lastName, dateOfBirth, email, phoneNumber) ;
            StudentDao studentDao = new StudentDao() ;
            studentDao.updateStudentInfo(student, firstName, lastName, dateOfBirth, email, phoneNumber);
             StudentInformationSystemImpl sis = new StudentInformationSystemImpl() ;
             
             
            System.out.println("Student information updated successfully.");
        } catch (Exception e) {
            System.out.println("Error: Invalid date format. Please enter the date in yyyy-MM-dd format.");
        }
    }

	private static void enrollStudentInCourse(StudentInformationSystemImpl sisImplementation) {
	    try {
	        System.out.print("Enter student ID: ");
	        int studentId = scanner.nextInt();
	        scanner.nextLine();

	        System.out.print("Enter course ID: ");
	        int courseId = scanner.nextInt();
	        scanner.nextLine();

	        System.out.print("Enter enrollment date (yyyy-MM-dd): ");
	        String date = scanner.nextLine();
	        Date enrollmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

	        StudentInformationSystemImpl sis = new StudentInformationSystemImpl();
	        sis.enrollStudentInCourse(studentId, courseId, enrollmentDate);

	        System.out.println("Student enrolled in the course successfully."); // ✅ moved inside try
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}


    private static void assignTeacherToCourse(StudentInformationSystemImpl sisImplementation) {
 try {
            System.out.print("Enter teacher ID: ");
            int teacherId = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            System.out.print("Enter course ID: ");
            int courseId = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

             StudentInformationSystemImpl sis = new StudentInformationSystemImpl() ;
                    sis.assignTeacherToCourse(teacherId,courseId);
            System.out.println("Teacher assigned to the course successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void recordPayment(StudentInformationSystemImpl sisImplementation) {
try {
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            System.out.print("Enter payment amount: ");
            BigDecimal amount = scanner.nextBigDecimal();
            scanner.nextLine();  // Consume the newline character

            System.out.print("Enter payment date (yyyy-MM-dd): ");
              String date = scanner.nextLine();
            Date PaymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            sisImplementation.recordPayment(studentId, amount, PaymentDate);
            System.out.println("Payment recorded successfully.");
            
        } catch (ParseException e) {
            System.out.println("Error: Invalid date format. Please enter the date in yyyy-MM-dd format.");
        }
    }

    private static void generateEnrollmentReport(StudentInformationSystemImpl sisImplementation) {
        try {
            System.out.print("Enter course ID: ");
            int courseId = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            List<Students> enrolledStudents = sisImplementation.generateEnrollmentReport(courseId);

            if (enrolledStudents == null) {
                // Error already printed inside the generateEnrollmentReport method
                return;
            }

            if (enrolledStudents.isEmpty()) {
                System.out.println("No students enrolled in this course.");
                return;
            }

            System.out.println("Enrolled Students in Course:");
            for (Students student : enrolledStudents) {
                System.out.println(student);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void generatePaymentReport(StudentInformationSystemImpl sisImplementation) {
        try {
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();  

            List<Payments> payments = sisImplementation.generatePaymentReport(studentId);

            if (payments == null) {
                return;
            }

            if (payments.isEmpty()) {
                System.out.println("No payments found for this student.");
                return;
            }

            System.out.println("Payments for Student:");
            for (Payments payment : payments) {
                System.out.println(payment);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
