CREATE DATABASE IF NOT EXISTS student_course_db;
USE student_course_db;

-- 1. ADMIN TABLE (Section 13.3)
CREATE TABLE admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Seed Data (Default Admin credential fallback rule - Section 13.3)
INSERT INTO admin (username, password)
VALUES ('admin', 'admin123');

-- 2. STUDENTS TABLE (Section 13.4)
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    age INT NOT NULL,
    city VARCHAR(50) NOT NULL,
    CONSTRAINT check_student_age CHECK (age >= 18) -- Page 8, Sec 9.3.3 rule
);

-- 3. COURSES TABLE (Section 13.5)
CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    duration VARCHAR(50) NOT NULL, -- Fixed: Kept as VARCHAR(50) per page 18 spec
    fees DOUBLE NOT NULL,          -- Fixed: Kept as DOUBLE per page 18 spec
    trainer_name VARCHAR(100) NOT NULL,
    CONSTRAINT check_course_fees CHECK (fees > 0.0) -- Page 10, Sec 9.4.3 rule
);

-- 4. REGISTRATIONS TABLE (Section 13.6)
CREATE TABLE registrations (
    registration_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    registration_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL, -- VARCHAR(20) matching allowed validation rules
    
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

-- 5. THE SRS COMPLIANCE TRIGGER (Page 13, Sec 9.5.4 Rule Enforcer)
DELIMITER $$

CREATE TRIGGER check_duplicate_active_registration
BEFORE INSERT ON registrations
FOR EACH ROW
BEGIN
    DECLARE active_count INT;

    -- Look up if this student already has an active slot for this course right now
    IF NEW.status = 'ACTIVE' THEN
        SELECT COUNT(*) INTO active_count 
        FROM registrations 
        WHERE student_id = NEW.student_id 
          AND course_id = NEW.course_id 
          AND status = 'ACTIVE';
          
        -- If an active entry is found, crash the insert transaction window with an error message
        IF active_count > 0 THEN
            SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Duplicate active registration is not allowed for this student and course.';
        END IF;
    END IF;
END$$

DELIMITER ;
