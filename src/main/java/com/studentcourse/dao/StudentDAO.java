package com.studentcourse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.studentcourse.model.Student;
import com.studentcourse.util.DBConnection;

public class StudentDAO {
	
    private Connection conn;
    
    public StudentDAO() {
        // Keeps fields uninitialized since methods fetch connections on-demand via DBConnection
    }

    // Fixed constructor to correctly map the passed connection dependency
    public StudentDAO(Connection conn) {
        this.conn = conn;
    }

    // Insert Student
    public boolean addStudent(Student student) throws SQLException {
        String query = "INSERT INTO students (student_name, email, phone, age, city) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, student.getStudentName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPhone());
            ps.setInt(4, student.getAge());
            ps.setString(5, student.getCity());

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // Get All Students
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentName(rs.getString("student_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setAge(rs.getInt("age"));
                student.setCity(rs.getString("city"));
                students.add(student);
            }
        }
        return students;
    }

    // Get Student By ID
    public Student getStudentById(int studentId) throws SQLException {
        Student student = null;
        String query = "SELECT * FROM students WHERE student_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    student = new Student();
                    student.setStudentId(rs.getInt("student_id"));
                    student.setStudentName(rs.getString("student_name"));
                    student.setEmail(rs.getString("email"));
                    student.setPhone(rs.getString("phone"));
                    student.setAge(rs.getInt("age"));
                    student.setCity(rs.getString("city"));
                }
            }
        }
        return student;
    }

    // Update Student
    public boolean updateStudent(Student student) throws SQLException {
        String query = "UPDATE students SET student_name = ?, email = ?, phone = ?, age = ?, city = ? WHERE student_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, student.getStudentName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPhone());
            ps.setInt(4, student.getAge());
            ps.setString(5, student.getCity());
            ps.setInt(6, student.getStudentId());

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // Check Student Registrations
    public boolean hasRegistrations(int studentId) throws SQLException {
        String query = "SELECT COUNT(*) FROM registrations WHERE student_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Delete Student
    public boolean deleteStudent(int studentId) throws SQLException {
        String query = "DELETE FROM students WHERE student_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, studentId);

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // Dashboard Count Alternative 1
    public int getStudentCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM students";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    // Dashboard Count Alternative 2 (Fixed NullPointerException)
    public int countStudents() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM students";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql); // Fixed: used local connection
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    // Search Students (Fixed Statement Order, Exceptions & Completeness)
    public List<Student> searchStudents(String keyword) throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE student_name LIKE ? OR email LIKE ? OR city LIKE ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
        	
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);

            try (ResultSet rs = ps.executeQuery()) { // Fixed: Executed after setting variables
                while (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("student_id"));
                    s.setStudentName(rs.getString("student_name"));
                    s.setEmail(rs.getString("email"));
                    s.setPhone(rs.getString("phone"));
                    s.setAge(rs.getInt("age"));
                    s.setCity(rs.getString("city"));
                    list.add(s);
                }
            }
        }
        return list;
    }
}