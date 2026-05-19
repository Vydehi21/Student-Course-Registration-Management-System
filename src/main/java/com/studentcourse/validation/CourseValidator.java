package com.studentcourse.validation;

import com.studentcourse.exception.InvalidCourseException;
import com.studentcourse.model.Course;

public class CourseValidator {

    public static void validate(Course c) throws InvalidCourseException {
        if (c == null) {
            throw new InvalidCourseException("Course details are required");
        }

        // Course Name Check
        if (c.getCourseName() == null || c.getCourseName().trim().isEmpty()) {
            throw new InvalidCourseException("Course name is required.");
        }
        
        if (!c.getCourseName().matches("[A-Za-z0-9 .-]+")) {

            throw new InvalidCourseException(
                    "Course name contains invalid characters.");
        }

        // Trainer Name Check
        if (c.getTrainerName() == null || c.getTrainerName().trim().isEmpty()) {
            throw new InvalidCourseException("Trainer name is required.");
        }
        
        if (!c.getTrainerName().matches("[A-Za-z .-]+")) {

            throw new InvalidCourseException(
                    "Trainer name contains invalid characters.");
        }

        if (c.getDuration() == null ||
        	    c.getDuration().trim().isEmpty()) {

        	    throw new InvalidCourseException(
        	            "Course duration is required.");
        	}

        	String duration =
        	        c.getDuration().trim();

        	if (!duration.matches("\\d+")) {

        	    throw new InvalidCourseException(
        	            "Duration must contain only positive numbers.");
        	}

        	if (Integer.parseInt(duration) <= 0) {

        	    throw new InvalidCourseException(
        	            "Duration must be greater than 0.");
        	}

        // Fees Validation
        if (c.getFees() <= 0) {
            throw new InvalidCourseException("Fees must be greater than 0");
        }
    }
}
