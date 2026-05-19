package com.studentcourse.validation;

import com.studentcourse.exception.InvalidLoginException;

public class AdminValidator {

    public static void validate(String username, String password)
            throws InvalidLoginException {

        if (username == null || username.trim().isEmpty()) {

            throw new InvalidLoginException(
                    "Username is required");
        }

        if (!username.matches("[A-Za-z0-9_]+")) {

            throw new InvalidLoginException(
                    "Username contains invalid characters");
        }

        if (password == null || password.trim().isEmpty()) {

            throw new InvalidLoginException(
                    "Password is required");
        }

        if (password.length() < 8) {

            throw new InvalidLoginException(
                    "Password must contain at least 8 characters");
        }
    }
}