# Interview Notes

## 30-Second Project Explanation

I built an Employee Management System using Core Java, JDBC, and MySQL. It is a console-based CRUD application where users can add, view, search, update, and delete employee records. I used a layered design with model, DAO, service, and UI classes. The DAO layer uses JDBC PreparedStatement and try-with-resources to interact safely with the MySQL database.

## Main Responsibilities In The Project

- Designed the MySQL employee table with primary key, unique email, indexes, and salary constraints.
- Implemented CRUD operations using JDBC.
- Used PreparedStatement to pass query parameters safely.
- Added validation for required fields, email format, salary, hire date, and status.
- Added search and department-wise salary summary using SQL aggregation.
- Organized code into model, DAO, service, UI, utility, and exception packages.

## Questions You May Be Asked

### Why did you use PreparedStatement?

PreparedStatement helps prevent SQL injection because user input is passed as parameters instead of being joined directly into the SQL string. It also makes repeated query execution cleaner.

### What is JDBC?

JDBC is a Java API used to connect and execute queries against databases. In this project, I used JDBC to connect Java with MySQL and perform insert, select, update, and delete operations.

### What is the DAO layer?

DAO means Data Access Object. It separates database logic from business logic. In this project, `EmployeeDao` defines the database operations and `JdbcEmployeeDao` implements them using JDBC.

### How did you handle database resources?

I used try-with-resources for `Connection`, `PreparedStatement`, and `ResultSet`. This automatically closes resources and avoids connection leaks.

### What SQL operations did you use?

I used INSERT, SELECT, UPDATE, DELETE, WHERE, LIKE, GROUP BY, COUNT, AVG, and SUM.

### How would you improve this project later?

I would add login authentication, role-based access, pagination, unit tests, a web UI using Spring Boot, and deployment with a hosted MySQL database.
