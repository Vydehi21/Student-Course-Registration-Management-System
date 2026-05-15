package com.studentcourse.dao;

import com.studentcourse.util.DBConnection;
import com.studentcourse.model.Registration;

import java.sql.*;
import java.util.*;

public class RegistrationDAO {

    // Default Constructor avoiding sticky structural tracking connections
    public RegistrationDAO() {}

    // Parametrized Constructor for dependency injection setups
    public RegistrationDAO(Connection conn) {}

    // INSERT
    public int registerStudent(Registration r) throws SQLException {
        String sql = "INSERT INTO registrations(student_id, course_id, registration_date, status) VALUES (?, ?, ?, ?)";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, r.getStudentId());
            ps.setInt(2, r.getCourseId());
            ps.setDate(3, r.getRegistrationDate());
            ps.setString(4, r.getStatus());

            return ps.executeUpdate();
            
        } catch (SQLException e) {
            // Check for the MySQL custom trigger error state code (45000)
            if ("45000".equals(e.getSQLState()) || e.getMessage().contains("Duplicate active registration")) {
                System.err.println("Validation Blocked: Student already holds an active enrollment profile.");
                // Throw an explicit message that our Servlet's catch block can read
                throw new SQLException("Duplicate active registration is not allowed for this student and course.", e);
            }
            throw e; // pass generic SQL exceptions through normally
        }
    }


    // LIST ALL 
    public List<Registration> getAllRegistrations() throws SQLException {
        List<Registration> list = new ArrayList<>();
        String sql = "SELECT r.registration_id, s.student_name, c.course_name, r.registration_date, r.status " +
                     "FROM registrations r " +
                     "JOIN students s ON r.student_id = s.student_id " +
                     "JOIN courses c ON r.course_id = c.course_id " +
                     "ORDER BY r.registration_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Registration registration = new Registration();
                registration.setRegistrationId(rs.getInt("registration_id"));
                registration.setStudentName(rs.getString("student_name"));
                registration.setCourseName(rs.getString("course_name"));
                registration.setRegistrationDate(rs.getDate("registration_date"));
                registration.setStatus(rs.getString("status"));
                
                list.add(registration);
            }
        }
        return list;
    }
    
    // UPDATE STATUS
    public int updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE registrations SET status=? WHERE registration_id=?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate();
        }
    }
    
    // DELETE REGISTRATION
    public int deleteRegistration(int id) throws SQLException {
        String sql = "DELETE FROM registrations WHERE registration_id=?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
    
    // DASHBOARD SUMMARY COUNT
    public int countRegistrations() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM registrations";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }
    
    // SEARCH FILTER
    public List<Registration> searchRegistrations(String keyword) throws SQLException {
        List<Registration> registrationList = new ArrayList<>();
        String sql = "SELECT r.registration_id, s.student_name, c.course_name, r.registration_date, r.status " +
                     "FROM registrations r " +
                     "JOIN students s ON r.student_id = s.student_id " +
                     "JOIN courses c ON r.course_id = c.course_id " +
                     "WHERE s.student_name LIKE ? OR c.course_name LIKE ? OR r.status LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String wrapperPattern = "%" + keyword + "%";
            ps.setString(1, wrapperPattern);
            ps.setString(2, wrapperPattern);
            ps.setString(3, wrapperPattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration registration = new Registration();
                    registration.setRegistrationId(rs.getInt("registration_id"));
                    registration.setStudentName(rs.getString("student_name"));
                    registration.setCourseName(rs.getString("course_name"));
                    registration.setRegistrationDate(rs.getDate("registration_date"));
                    registration.setStatus(rs.getString("status"));

                    registrationList.add(registration);
                }
            }
        }
        return registrationList;
    }
}
