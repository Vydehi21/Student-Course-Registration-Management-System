# Student Course Registration System

This is a clean, Servlet-based web application designed to manage student enrollments, course catalogs, and admin control workflows. It is built entirely on native Java web technologies like Servlets, JSPs, and JDBC, following standard Model-View-Controller (MVC) architecture principles without relying on high-level frameworks like Spring Boot or Hibernate.

## Features
* **Admin Login & Dashboard:** Core credentials validation with a dashboard showcasing total summary metrics across all entities.
* **Session Management:** Secure access guards protect internal page views from unauthenticated URL access.
* **Remember Me Cookies:** Optional checkbox remembers the login username for returning sessions via an HTTP-only cookie.
* **Student CRUD:** Complete workflow for adding, viewing, updating, and removing student details safely.
* **Course CRUD:** Manage details like course names, instructors, tuition fees, and duration tracking.
* **Enrollment Registry:** Connects students to courses while automatically enforcing constraints against multiple active enrollments.

## Tech Stack
* **Language Environment:** Java 17 / 21
* **Web Technology:** Jakarta Servlets 6.0 & JSP 3.1
* **Database Engine:** MySQL 8.x
* **Server Target:** Apache Tomcat 10.x
* **Build Utility:** Apache Maven 3.8+

## 📂 Project Directory Structure

```text
student-course-app/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── studentcourse/
│       │           │
│       │           ├── controller/           # MVC Controllers (Servlets)
│       │           │   ├── AddCourseServlet.java
│       │           │   ├── AddStudentServlet.java
│       │           │   ├── DashboardServlet.java
│       │           │   ├── DeleteCourseServlet.java
│       │           │   ├── DeleteRegistrationServlet.java
│       │           │   ├── DeleteStudentServlet.java
│       │           │   ├── EditCourseServlet.java
│       │           │   ├── EditStudentServlet.java
│       │           │   ├── LoginPageServlet.java
│       │           │   ├── LoginServlet.java
│       │           │   ├── LogoutServlet.java
│       │           │   ├── RegisterStudentCourseServlet.java
│       │           │   ├── RegistrationFormServlet.java
│       │           │   ├── UpdateCourseServlet.java
│       │           │   ├── UpdateRegistrationStatusServlet.java
│       │           │   ├── UpdateStudentServlet.java
│       │           │   ├── ViewCoursesServlet.java
│       │           │   ├── ViewRegistrationsServlet.java
│       │           │   └── ViewStudentsServlet.java
│       │           │
│       │           ├── dao/                  # Database Logic Layers (DAOs)
│       │           │   ├── AdminDAO.java
│       │           │   ├── CourseDAO.java
│       │           │   ├── RegistrationDAO.java
│       │           │   └── StudentDAO.java
│       │           │
│       │           ├── exception/            # Custom Domain Exceptions
│       │           │   ├── InvalidCourseException.java
│       │           │   ├── InvalidLoginException.java
│       │           │   ├── InvalidRegistrationException.java
│       │           │   └── InvalidStudentException.java
│       │           │
│       │           ├── filter/               # Global Authentication Interceptors
│       │           │   └── AuthFilter.java
│       │           │
│       │           ├── model/                # Data Entities
│       │           │   ├── Admin.java
│       │           │   ├── Course.java
│       │           │   ├── Registration.java
│       │           │   └── Student.java
│       │           │
│       │           ├── util/                 # Connection Resource Pooling Utilities
│       │           │   └── DBConnection.java
│       │           │
│       │           └── validation/           # Domain Layer Business Validators
│       │               ├── AdminValidator.java
│       │               ├── CourseValidator.java
│       │               ├── RegistrationValidator.java
│       │               └── StudentValidator.java
│       │
│       └── webapp/
│           ├── css/
│           │   └── style.css                 # Master Application Theme Layout stylesheet
│           │
│           ├── WEB-INF/
│           │   ├── web.xml                   # System Deployment Descriptor Mapping File
│           │   └── views/                    # Secured View Layer (JSPs hidden from direct access)
│           │       ├── course-edit.jsp
│           │       ├── course-form.jsp
│           │       ├── course-list.jsp
│           │       ├── dashboard.jsp
│           │       ├── error.jsp
│           │       ├── login.jsp
│           │       ├── registration-form.jsp
│           │       ├── registration-list.jsp
│           │       ├── student-edit.jsp
│           │       ├── student-form.jsp
│           │       └── student-list.jsp
│           │
│           └── index.jsp                     # Root Context Redirect Handler Entry File
│
└── pom.xml                                   # Dependency & Build Configuration Profile
```

## 🔒 Session & Cookie Constraints Validation
*   **Security Interception Framework:** Every single protected workspace route is intercepted by `AuthFilter.java`. Unauthenticated traffic trying to access metrics dashboard endpoints or data views is automatically blocked and redirected to `/login`.
*   **Cookie Retention Rules:** If the admin selects **Remember Username**, the application sets an HTTP-only `rememberedUsername` cookie with a 7-day lifespan. If left unchecked, any existing configuration cookie is explicitly destroyed.
*   **Post-Logout Session Hardening:** Executing a session logout invokes explicit `.invalidate()` routines on server caches. This prevents back-button browser caching from exposing system metric summaries once unauthenticated.

## 🚀 Environment Execution & Deployment Guide

### Prerequisites
Ensure you have the following installed on your machine:
*   Java Development Kit (JDK 17 or 21)
*   MySQL Server (8.x)
*   Eclipse IDE for Enterprise Java and Web Developers
*   Apache Tomcat 10.x

### Step 1: Database Initialization
1. Open your MySQL Command Line Client or any GUI tool like MySQL Workbench / phpMyAdmin.
2. Copy, paste, and execute the database schema script provided in the section below to create the database, tables, seed the default admin account, and compile the verification trigger.

### Step 2: Import Project into Eclipse
1. Open Eclipse Enterprise Edition.
2. Go to **File -> Import...**
3. Select **Maven -> Existing Maven Projects** and click **Next**.
4. Browse to the root folder of this project (where `pom.xml` is located) and click **Finish**.
5. Wait for Eclipse to resolve the required Jakarta dependencies listed in your `pom.xml`.

### Step 3: Configure Database Connection
1. Open the file `src/main/java/com/studentcourse/util/DBConnection.java`.
2. Update the database URL, username, and password parameters to match your local MySQL server setup.

### Step 4: Configure Apache Tomcat Server in Eclipse
1. Locate the **Servers** tab at the bottom of Eclipse (If missing, go to *Window -> Show View -> Servers*).
2. Click the link to create a new server, select **Apache -> Tomcat v10.0 Server**, and click **Next**.
3. Browse and point to your local Tomcat installation directory, then click **Finish**.

### Step 5: Run and Deploy the Application
1. Right-click the root project folder in your Project Explorer panel.
2. Choose **Run As -> Run on Server**.
3. Select your configured Tomcat 10 server instance and click **Finish**.
4. Once the server logs turn active, open your web browser and navigate to: `http://localhost:8080/student-course-app/login`
5. Use the default login credentials: 
   * **Username:** `admin`
   * **Password:** `admin123`

---

## 🗄️ Database Setup Script

Execute this setup script within your local MySQL environment configuration workspace:

```sql
CREATE DATABASE IF NOT EXISTS student_course_db;
USE student_course_db;

-- 1. ADMIN TABLE
CREATE TABLE admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Seed default admin account
INSERT INTO admin (username, password)
VALUES ('admin', 'admin123')
ON DUPLICATE KEY UPDATE username=username;

-- 2. STUDENTS TABLE
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    age INT NOT NULL,
    city VARCHAR(50) NOT NULL,
    CONSTRAINT check_student_age CHECK (age >= 18) 
);

-- 3. COURSES TABLE
CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    duration VARCHAR(50) NOT NULL, 
    fees DOUBLE NOT NULL,          
    trainer_name VARCHAR(100) NOT NULL,
    CONSTRAINT check_course_fees CHECK (fees > 0.0)
);

-- 4. REGISTRATIONS TABLE
CREATE TABLE registrations (
    registration_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    registration_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL, 
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

-- 5. DUPLICATE ACTIVE ENROLLMENT CONSTRAINT TRIGGER
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
```
