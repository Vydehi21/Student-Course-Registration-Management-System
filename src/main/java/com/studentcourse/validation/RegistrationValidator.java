package com.studentcourse.validation;

import com.studentcourse.exception.InvalidRegistrationException;
import com.studentcourse.model.Registration;

public class RegistrationValidator {

    public static void validate(Registration r) throws InvalidRegistrationException {
        if (r == null) {
            throw new InvalidRegistrationException("Registration dataset cannot be null.");
        }

        if (r.getStudentId() <= 0) {
            throw new InvalidRegistrationException("Invalid student entity selection mapping target.");
        }

        if (r.getCourseId() <= 0) {
            throw new InvalidRegistrationException("Invalid academic course entity selection mapping target.");
        }

        if (r.getRegistrationDate() == null) {
            throw new InvalidRegistrationException("Registration transaction completion date is required.");
        }

        if (r.getStatus() == null || r.getStatus().trim().isEmpty()) {
            throw new InvalidRegistrationException("Operational processing state status string is required.");
        }
    }
}
