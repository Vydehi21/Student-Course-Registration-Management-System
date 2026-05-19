package com.studentcourse.model;

public class Course {

    private int courseId;
    private String courseName;
    private String trainerName;
    private String duration; 
    private double fees;   

    public Course() {
    }

    // Constructor without ID
    public Course(String courseName, String trainerName, String duration, double fees) {
        this.courseName = courseName;
        this.trainerName = trainerName;
        this.duration = duration;
        this.fees = fees;
    }

    // Full constructor with ID
    public Course(int courseId, String courseName, String trainerName, String duration, double fees) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.trainerName = trainerName;
        this.duration = duration;
        this.fees = fees;
    }

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

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }
}
