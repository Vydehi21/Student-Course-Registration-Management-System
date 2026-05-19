package com.studentcourse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.studentcourse.util.DBConnection;

public class AdminDAO {

    public AdminDAO() {}
    
    private static final String LOGIN_QUERY = "SELECT admin_id, username FROM admin WHERE username = ? AND password = ?";

    public boolean validateAdmin(String username, String password) {
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
