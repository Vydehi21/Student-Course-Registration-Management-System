# Student Course Registration & Management System

A robust, enterprise-ready Java Servlet-based web application constructed strictly in adherence to the official Project Software Requirements Specification (SRS). The application implements a decoupled Model-View-Controller (MVC) architecture using core Java Web technology, robust server-side structural data validators, customized transactional constraint checking, and transactional connection-pool thread safety.

## 📌 Project Overview & Intent
The core intent of this application is to demonstrate mastery of standard Java Web stack fundamentals without dependency on advanced frameworks like Spring or Hibernate. Key elements implemented include:
*   **Decoupled Controller Mapping Layers:** Explicit 1-to-1 mappings matching Servlet URL configuration specs.
*   **Stateful Security Operations:** Session-based authentication guards alongside Cookie memory managers.
*   **Database Transaction Lifecycle Control:** Thread-safe DAO operations wrapped in local auto-close resource handlers.
*   **Strict Relational Consistency:** Embedded database-level `BEFORE INSERT` triggers safeguarding multi-row status integrity.

---

## 🛠️ Technology Stack Matrix
*   **Language Environment:** Java SE 17 / 21
*   **Backend Architecture:** Jakarta Servlets 6.0 / Java EE Core Servlets
*   **Presentation Layer:** JavaServer Pages (JSP) 3.1
*   **Database Engine:** MySQL 8.x
*   **Connectivity API:** Java Database Connectivity (JDBC)
*   **Target Application Server:** Apache Tomcat 10.x
*   **Build & Lifecycle Utility:** Apache Maven 3.8+

---

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

---

## 🔒 Session & Cookie Constraints Validation
*   **Security Interception Framework:** Every single protected workflow route is intercepted dynamically by `AuthFilter.java`. Unauthenticated traffic trying to access dashboard endpoints or data tables is immediately blocked and redirected via `sendRedirect` to `/login`.
*   **Cookie Retention Rules:** If the admin selects **Remember Username**, the application sets a secure, HTTP-only `rememberedUsername` cookie with a 7-day expiration lifespan. If left unchecked, any existing configuration cookie is explicitly destroyed at the client node.
*   **Post-Logout Session Hardening:** Executing a session logout invokes explicit `.invalidate()` routines on server caches. This safely prevents back-button browser caching from exposing system metric summaries once unauthenticated.

---

## 🗄️ Database Installation & Compliance Script

Execute this setup script within your local MySQL environment initialization terminal:

```sql
CREATE DATABASE IF NOT EXISTS student_course_db;
USE student_course_db;

-- 1. ADMIN TABLE (Section 13.3)
CREATE TABLE admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Seed Default Mandatory Admin Credentials (Section 13.3)
INSERT INTO admin (username, password) 
VALUES ('admin', 'admin123')
ON DUPLICATE KEY UPDATE username=username;

-- 2. STUDENTS TABLE (Section 13.4)
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    age INT NOT NULL,
    city VARCHAR(50) NOT NULL,
    CONSTRAINT check_student_age CHECK (age >= 18)
);

-- 3. COURSES TABLE (Section 13.5)
CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    duration VARCHAR(50) NOT NULL,
    fees DOUBLE NOT NULL,
    trainer_name VARCHAR(100) NOT NULL,
    CONSTRAINT check_course_fees CHECK (fees > 0.0)
);

-- 4. REGISTRATIONS TABLE (Section 13.6)
CREATE TABLE registrations (
    registration_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    registration_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

-- 5. THE SRS SPECIFICATION ENFORCEMENT TRIGGER (Section 9.5.4 Duplicate Rule)
-- Traditional UNIQUE combinations block users from re-registering for completed or dropped courses.
-- This trigger blocks duplicate rows ONLY if an existing enrollment profile status is active.
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

---

## 🚀 Environment Execution & Deployment Guide

Follow these steps to deploy and run the application locally inside your enterprise IDE environment workspace:

### Step 1: Database Baseline Configuration
1. Open your native MySQL terminal and run the complete contents of the compilation schema script documented above.
2. Verify table creation by running `SHOW TABLES;`.
3. Open `com.project.app.util.DBConnection.java` or `db.properties` and verify your local server port variables, username, and password tokens match perfectly.

### Step 2: Clean and Pack the Artifact
Navigate to the application root directory (where your `pom.xml` resides) using your terminal and run the standard Maven phase assembly triggers:
```bash
mvn clean package
```
This builds a deployment-ready executable target file named `student-course-app.war` inside your local directory structure.

### Step 3: Run the Application on Apache Tomcat Server
#### Option A: Running from Eclipse Enterprise / IntelliJ
1. Right-click the application root folder project node.
2. Select **Run As** $\rightarrow$ **Run on Server**.
3. Select your pre-configured Apache Tomcat 10+ server and click **Finish**.

#### Option B: Deploying the Standalone WAR Artifact
1. Copy the compiled `student-course-app.war` artifact file out of your `target/` directory.
2. Drop the artifact directly into the structural runtime execution path directory of your standalone container server: `/tomcat/webapps/`.
3. Start the server via your system command execution environment lines: `/tomcat/bin/startup.sh` or `startup.bat`.

### Step 4: Access and Use the Application
*   Open your web browser and navigate to the application entrance point URL path:
    `http://localhost:8080/student-course-app/`
*   The application automatically activates your `index.jsp` route and pushes traffic to the standalone rendering path `/login`.
*   **Default Evaluation Login Credentials:**
    *   **Username:** `admin`
    *   **Password:** `admin123`

---

## 🧪 Console Verification & Lifecycle Evidence
As mandated explicitly by **Section 12.2** of your evaluation constraints, at least four primary core servlets execute strict lifecycle console reporting methods during system initialize and execution events. 

Open your system environment logging output or terminal console to verify these explicit confirmation string parameters print natively during deployment and processing activities:
*   `LoginPageServlet initialized successfully via init()`
*   `LoginServlet initialized successfully via init()`
*   `DashboardServlet initialized successfully via init()`
*   `AddStudentServlet initialized successfully via init()`
*   `AddCourseServlet initialized successfully via init()`
