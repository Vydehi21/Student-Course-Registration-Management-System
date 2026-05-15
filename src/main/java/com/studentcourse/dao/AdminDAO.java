package com.studentcourse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.studentcourse.util.DBConnection;

public class AdminDAO {

    // Fixed: Removed unmanaged class-level field caching to avoid leak states
    public AdminDAO() {
    }
    
    private static final String LOGIN_QUERY = "SELECT * FROM admin WHERE username = ? AND password = ?";

    public boolean validateAdmin(String username, String password) {
        // Safe: Every transaction fetches and auto-disposes its execution connection locally
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(LOGIN_QUERY)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
