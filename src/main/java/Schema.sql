CREATE DATABASE IF NOT EXISTS student_course_db;
USE student_course_db;

CREATE TABLE admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

INSERT INTO admin (username, password)
VALUES ('admin', 'admin123');

CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    age INT NOT NULL,
    city VARCHAR(50) NOT NULL,
    CONSTRAINT check_student_age CHECK (age >= 18) 
);

CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    duration VARCHAR(50) NOT NULL, 
    fees DOUBLE NOT NULL,          
    trainer_name VARCHAR(100) NOT NULL,
    CONSTRAINT check_course_fees CHECK (fees > 0.0)
);


CREATE TABLE registrations (
    registration_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    registration_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL, 
    
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

DELIMITER $$

CREATE TRIGGER check_duplicate_active_registration
BEFORE INSERT ON registrations
FOR EACH ROW
BEGIN
    DECLARE active_count INT;

   
    IF NEW.status = 'ACTIVE' THEN
        SELECT COUNT(*) INTO active_count 
        FROM registrations 
        WHERE student_id = NEW.student_id 
          AND course_id = NEW.course_id 
          AND status = 'ACTIVE';
          
       
        IF active_count > 0 THEN
            SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Duplicate active registration is not allowed for this student and course.';
        END IF;
    END IF;
END$$

DELIMITER ;
