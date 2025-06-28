package com.hexaware.studentinformationsystem.model;


import java.util.Date;

public class Students {
    private static int StudentId;
    private String firstName;
    private String lastName;
    private Date dob;
    private String email;
    private String phone;

    public Students(int studentId, String firstName, String lastName, Date dateOfBirth, String email, String phone) {
        this.StudentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dateOfBirth;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return StudentId + " | " + firstName + " " + lastName + " | " + dob + " | " + email + " | " + phone;
    }
    public Students() {
        // This is required to create object like: new Students()
    }



	public static int getStudentId() {
		return StudentId;
	}

	public void setStudentId(int studentId) {
		StudentId = studentId;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
}
