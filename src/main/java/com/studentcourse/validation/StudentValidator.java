package com.studentcourse.validation;

import com.studentcourse.exception.InvalidStudentException;
import com.studentcourse.model.Student;

public class StudentValidator {

    public static void validate(Student s)
            throws InvalidStudentException {

        if (s.getStudentName() == null ||
            s.getStudentName().trim().isEmpty()) {

            throw new InvalidStudentException(
                    "Student name is required");
        }

        if (s.getEmail() == null ||
        	    !s.getEmail().matches(
        	    "^[A-Za-z0-9+_.-]+@(.+)$")) {

        	    throw new InvalidStudentException(
        	            "Invalid email format");
        	}
        if (s.getPhone() == null ||
        	    !s.getPhone().matches("\\d{10}")) {

        	    throw new InvalidStudentException(
        	            "Phone must contain exactly 10 digits");
        	}

        if (s.getAge() < 18) {

            throw new InvalidStudentException(
                    "Age must be be 18 or above");
        }
        
        if (s.getAge() > 100) {

            throw new InvalidStudentException(
                    "Invalid age entered");
        }

        if (s.getCity() == null ||
            s.getCity().trim().isEmpty()) {

            throw new InvalidStudentException(
                    "City is required");
        }
    }
}