package com.studentcourse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.studentcourse.model.Course;
import com.studentcourse.util.DBConnection;

public class CourseDAO {

    // Default constructor leaving fields uninitialized to follow safe pooling rules
    public CourseDAO() {}

    // Parametrized constructor for optional manual connection passing dependency
    public CourseDAO(Connection conn) {}

    // Add Course
    public boolean addCourse(Course course) throws Exception {
        String query = "INSERT INTO courses (course_name, trainer_name, duration, fees) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getTrainerName());
            ps.setString(3, course.getDuration()); // Fixed: setString for VARCHAR tracking
            ps.setDouble(4, course.getFees());

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // View All Courses
    public List<Course> getAllCourses() throws Exception {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setTrainerName(rs.getString("trainer_name"));
                course.setDuration(rs.getString("duration")); // Fixed: getString matching database
                course.setFees(rs.getDouble("fees"));

                courses.add(course);
            }
        }
        return courses;
    }

    // Get Course By ID
    public Course getCourseById(int courseId) throws Exception {
        Course course = null;
        String query = "SELECT * FROM courses WHERE course_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setTrainerName(rs.getString("trainer_name"));
                    course.setDuration(rs.getString("duration")); // Fixed: Aligned to String
                    course.setFees(rs.getDouble("fees"));
                }
            }
        }
        return course;
    }

    // Update Course
    public boolean updateCourse(Course course) throws Exception {
        String query = "UPDATE courses SET course_name = ?, trainer_name = ?, duration = ?, fees = ? WHERE course_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getTrainerName());
            ps.setString(3, course.getDuration()); // Fixed: String mapping
            ps.setDouble(4, course.getFees());
            ps.setInt(5, course.getCourseId());

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // Delete Course
    public boolean deleteCourse(int courseId) throws Exception {
        String query = "DELETE FROM courses WHERE course_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, courseId);

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }
    
    // Added: Fixed constraint verification method required by DeleteCourseServlet
    public boolean hasActiveRegistrations(int courseId) throws SQLException {
        String query = "SELECT COUNT(*) FROM registrations WHERE course_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    // Dashboard Course Counting Summary
    public int countCourses() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM courses";
        
        // Fixed: Moved to try-with-resources statement execution block to avoid leaks
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    // Search Filter Engine
    public List<Course> searchCourses(String keyword) {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE course_name LIKE ? OR trainer_name LIKE ?";

        // Fixed: Enclosed ResultSet within try statement bounds to guarantee auto-cleanup
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setDuration(rs.getString("duration")); // Fixed: Formatted data extractor
                    course.setFees(rs.getDouble("fees"));
                    course.setTrainerName(rs.getString("trainer_name"));

                    courseList.add(course);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courseList;
    }
}
