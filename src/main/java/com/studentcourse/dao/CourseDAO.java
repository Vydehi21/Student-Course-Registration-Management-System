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

    public CourseDAO() {}

    public boolean addCourse(Course course) throws Exception {
        String query = "INSERT INTO courses (course_name, trainer_name, duration, fees) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getTrainerName());
            ps.setString(3, course.getDuration()); 
            ps.setDouble(4, course.getFees());

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

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
                course.setDuration(rs.getString("duration")); 
                course.setFees(rs.getDouble("fees"));

                courses.add(course);
            }
        }
        return courses;
    }

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
                    course.setDuration(rs.getString("duration")); 
                    course.setFees(rs.getDouble("fees"));
                }
            }
        }
        return course;
    }

    public boolean updateCourse(Course course) throws Exception {
        String query = "UPDATE courses SET course_name = ?, trainer_name = ?, duration = ?, fees = ? WHERE course_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getTrainerName());
            ps.setString(3, course.getDuration()); 
            ps.setDouble(4, course.getFees());
            ps.setInt(5, course.getCourseId());

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    public boolean deleteCourse(int courseId) throws Exception {
        String query = "DELETE FROM courses WHERE course_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, courseId);

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }
    
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
    
    public int countCourses() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM courses";
        
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
    
    public List<Course> searchCourses(String keyword) {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE course_name LIKE ? OR trainer_name LIKE ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            if (keyword == null) {
                keyword = "";
            }
            String searchKeyword = "%" + keyword + "%";

            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setCourseId(rs.getInt("course_id"));
                    course.setCourseName(rs.getString("course_name"));
                    course.setDuration(rs.getString("duration")); 
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
