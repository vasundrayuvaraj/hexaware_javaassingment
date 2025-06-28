package com.hexaware.studentinformationsystem.model;




import java.util.Date;

public class Enrollments {

	private int enrollmentId;
	private int studentId; 
	private int courseId; 
	private Date enrollmentDate;

	public Enrollments(int enrollmentId, int studentId, int courseId, Date enrollmentDate) {
		this.enrollmentId = enrollmentId;
		this.studentId = studentId;
		this.courseId = courseId;
		this.enrollmentDate = enrollmentDate;
	}

	public Enrollments(int studentId, int courseId) {
		// TODO Auto-generated constructor stub
		this.studentId = studentId;
        this.courseId = courseId;
	}

	public int getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(int enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	@Override
	public String toString() {
		return "Enrollments [enrollmentId=" + enrollmentId + ", studentId=" + studentId + ", courseId=" + courseId
				+ ", enrollmentDate=" + enrollmentDate + "]";
	}

}
