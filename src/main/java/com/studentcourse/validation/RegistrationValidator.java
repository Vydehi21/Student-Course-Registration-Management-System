package com.studentcourse.validation;

import java.sql.Date;

import com.studentcourse.exception.InvalidRegistrationException;
import com.studentcourse.model.Registration;

public class RegistrationValidator {

    public static void validate(Registration r) throws InvalidRegistrationException {
        if (r == null) {
            throw new InvalidRegistrationException("Registration details are required");
        }

        if (r.getStudentId() <= 0) {
            throw new InvalidRegistrationException("Invalid student entity selection mapping target.");
        }

        if (r.getCourseId() <= 0) {
            throw new InvalidRegistrationException("Invalid course selected");
        }
        
        Date registrationDate =
                r.getRegistrationDate();

        if (registrationDate == null) {

            throw new InvalidRegistrationException(
                    "Registration date is required");
        }

        Date today =
                new Date(System.currentTimeMillis());

        if (registrationDate.after(today)) {

            throw new InvalidRegistrationException(
                    "Future dates are not allowed");
        }

        if (r.getStatus() == null || r.getStatus().trim().isEmpty()) {
            throw new InvalidRegistrationException("Status is required");
        }
    }
}
