package com.hexaware.studentinformationsystem.model;



public class Teacher {
	private int teacherId;
	private String firstName;
	private String lastName;
	private String email;


	public Teacher(int teacherId, String firstName, String lastName, String email) {
		this.teacherId = teacherId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Teacher(int teacherId) {
		// TODO Auto-generated constructor stub
		 this.teacherId = teacherId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Teacher [teacherId=" + teacherId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + "]";
	}

}
