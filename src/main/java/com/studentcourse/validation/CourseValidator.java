package com.studentcourse.validation;

import com.studentcourse.exception.InvalidCourseException;
import com.studentcourse.model.Course;

public class CourseValidator {

    public static void validate(Course c) throws InvalidCourseException {
        if (c == null) {
            throw new InvalidCourseException("Course entity cannot be completely empty.");
        }

        // Course Name Check
        if (c.getCourseName() == null || c.getCourseName().trim().isEmpty()) {
            throw new InvalidCourseException("Course name is required.");
        }

        // Trainer Name Check
        if (c.getTrainerName() == null || c.getTrainerName().trim().isEmpty()) {
            throw new InvalidCourseException("Trainer name is required.");
        }

        // Fixed: Switched from numeric bounds evaluation to comprehensive text parsing validation
        if (c.getDuration() == null || c.getDuration().trim().isEmpty()) {
            throw new InvalidCourseException("Course duration window is a required structural tracking metric.");
        }

        // Fees Validation
        if (c.getFees() <= 0) {
            throw new InvalidCourseException("Financial pricing validation error: Fees must be greater than 0.");
        }
    }
}
