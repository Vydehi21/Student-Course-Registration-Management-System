package com.studentcourse.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    private DBConnection() {
    }

    static {
        String tempUrl = null;
        String tempUser = null;
        String tempPassword = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties props = new Properties();
            try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input != null) {
                    props.load(input);
                    tempUrl = props.getProperty("db.url");
                    tempUser = props.getProperty("db.user");
                    tempPassword = props.getProperty("db.password");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Hardcoding safety fix: Fallback to defaults ONLY if properties file reading fails
        URL = (tempUrl != null) ? tempUrl : "jdbc:mysql://localhost:3306/student_course_db";
        USER = (tempUser != null) ? tempUser : "root";
        PASSWORD = (tempPassword != null) ? tempPassword : "Puppy2107@";
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
