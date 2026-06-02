# Employee Management System

A Core Java console application that manages employee records using JDBC and MySQL. This project is built for Java/SQL fresher and junior developer resumes, with clean layers for UI, service validation, DAO, and database access.

## Features

- Add a new employee
- View all employees
- Find employee by ID
- Search employees by name, department, or job title
- Update employee details
- Delete employee records
- View department-wise employee count, average salary, and total salary
- Validate employee input before database operations
- Use PreparedStatement to prevent SQL injection

## Tech Stack

- Core Java
- JDBC
- MySQL
- Maven
- SQL

## Project Structure

```text
employee-management-system/
├── pom.xml
├── sql/
│   ├── schema.sql
│   └── sample-data.sql
└── src/main/
    ├── java/com/abhijit/ems/
    │   ├── App.java
    │   ├── config/
    │   ├── dao/
    │   ├── exception/
    │   ├── model/
    │   ├── service/
    │   ├── ui/
    │   └── util/
    └── resources/
        └── db.properties.example
```

## Database Setup

1. Open MySQL Workbench or MySQL command line.
2. Run the schema script:

```sql
SOURCE sql/schema.sql;
```

3. Optional: insert sample records:

```sql
SOURCE sql/sample-data.sql;
```

4. Copy `src/main/resources/db.properties.example` to `src/main/resources/db.properties`.
5. Update your MySQL username and password:

```properties
db.url=jdbc:mysql://localhost:3306/employee_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.user=root
db.password=your_mysql_password
```

You can also configure the app with environment variables:

```text
EMS_DB_URL
EMS_DB_USER
EMS_DB_PASSWORD
```

## Run The App

```bash
mvn clean compile
mvn exec:java
```

## Menu Options

```text
1. Add employee
2. View all employees
3. Find employee by ID
4. Search employees
5. Update employee
6. Delete employee
7. Department salary summary
8. Exit
```

## SQL Concepts Used

- CREATE DATABASE
- CREATE TABLE
- PRIMARY KEY
- UNIQUE constraint
- INSERT
- SELECT
- UPDATE
- DELETE
- WHERE
- LIKE
- GROUP BY
- COUNT
- AVG
- SUM
- Indexes

## Java Concepts Used

- Classes and objects
- Encapsulation
- Interfaces
- Exception handling
- Collections
- Enum
- POJO model classes
- JDBC Connection
- PreparedStatement
- ResultSet
- Try-with-resources
- Layered architecture

## Interview Explanation

This project follows a layered structure:

- `ConsoleMenu` handles user interaction.
- `EmployeeService` validates data and manages business logic.
- `EmployeeDao` defines database operations.
- `JdbcEmployeeDao` implements those operations using JDBC.
- `DatabaseConfig` loads database connection settings.

The project uses `PreparedStatement` for secure parameterized queries and `try-with-resources` to close database resources automatically.
