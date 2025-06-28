package com.hexaware.studentinformationsystem.model;



public class Courses {
    
    private int courseId;
    private String courseName;
    private int credits;
    private int teacherId;

    // Full constructor
    public Courses(int courseId, String courseName, int credits, int teacherId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.teacherId = teacherId;
    }

    // Constructor with only ID and name (if needed)
    public Courses(int courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
