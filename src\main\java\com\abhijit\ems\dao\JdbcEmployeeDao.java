package com.abhijit.ems.dao;

import com.abhijit.ems.config.DatabaseConfig;
import com.abhijit.ems.model.DepartmentSalarySummary;
import com.abhijit.ems.model.Employee;
import com.abhijit.ems.model.EmployeeStatus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcEmployeeDao implements EmployeeDao {
    private final DatabaseConfig databaseConfig;

    public JdbcEmployeeDao(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public Employee create(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees "
                + "(first_name, last_name, email, phone, department, job_title, salary, hire_date, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindEmployeeFields(statement, employee);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setEmployeeId(generatedKeys.getInt(1));
                }
            }
            return employee;
        }
    }

    @Override
    public Optional<Employee> findById(int employeeId) throws SQLException {
        String sql = "SELECT employee_id, first_name, last_name, email, phone, department, "
                + "job_title, salary, hire_date, status "
                + "FROM employees "
                + "WHERE employee_id = ?";

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapEmployee(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        String sql = "SELECT employee_id, first_name, last_name, email, phone, department, "
                + "job_title, salary, hire_date, status "
                + "FROM employees "
                + "ORDER BY employee_id";

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            return mapEmployees(resultSet);
        }
    }

    @Override
    public List<Employee> searchByNameOrDepartment(String keyword) throws SQLException {
        String sql = "SELECT employee_id, first_name, last_name, email, phone, department, "
                + "job_title, salary, hire_date, status "
                + "FROM employees "
                + "WHERE LOWER(first_name) LIKE ? "
                + "OR LOWER(last_name) LIKE ? "
                + "OR LOWER(department) LIKE ? "
                + "OR LOWER(job_title) LIKE ? "
                + "ORDER BY employee_id";

        String searchText = "%" + keyword.toLowerCase() + "%";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, searchText);
            statement.setString(2, searchText);
            statement.setString(3, searchText);
            statement.setString(4, searchText);

            try (ResultSet resultSet = statement.executeQuery()) {
                return mapEmployees(resultSet);
            }
        }
    }

    @Override
    public boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employees "
                + "SET first_name = ?, "
                + "last_name = ?, "
                + "email = ?, "
                + "phone = ?, "
                + "department = ?, "
                + "job_title = ?, "
                + "salary = ?, "
                + "hire_date = ?, "
                + "status = ? "
                + "WHERE employee_id = ?";

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bindEmployeeFields(statement, employee);
            statement.setInt(10, employee.getEmployeeId());
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(int employeeId) throws SQLException {
        String sql = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean emailExists(String email, Integer excludingEmployeeId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total "
                + "FROM employees "
                + "WHERE LOWER(email) = LOWER(?) "
                + "AND (? IS NULL OR employee_id <> ?)";

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            if (excludingEmployeeId == null) {
                statement.setNull(2, java.sql.Types.INTEGER);
                statement.setNull(3, java.sql.Types.INTEGER);
            } else {
                statement.setInt(2, excludingEmployeeId);
                statement.setInt(3, excludingEmployeeId);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt("total") > 0;
            }
        }
    }

    @Override
    public List<DepartmentSalarySummary> getDepartmentSalarySummary() throws SQLException {
        String sql = "SELECT department, "
                + "COUNT(*) AS employee_count, "
                + "AVG(salary) AS average_salary, "
                + "SUM(salary) AS total_salary "
                + "FROM employees "
                + "GROUP BY department "
                + "ORDER BY department";

        List<DepartmentSalarySummary> summaries = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                summaries.add(new DepartmentSalarySummary(
                        resultSet.getString("department"),
                        resultSet.getInt("employee_count"),
                        resultSet.getBigDecimal("average_salary"),
                        resultSet.getBigDecimal("total_salary")
                ));
            }
            return summaries;
        }
    }

    private void bindEmployeeFields(PreparedStatement statement, Employee employee) throws SQLException {
        statement.setString(1, employee.getFirstName());
        statement.setString(2, employee.getLastName());
        statement.setString(3, employee.getEmail());
        statement.setString(4, employee.getPhone());
        statement.setString(5, employee.getDepartment());
        statement.setString(6, employee.getJobTitle());
        statement.setBigDecimal(7, employee.getSalary());
        statement.setDate(8, Date.valueOf(employee.getHireDate()));
        statement.setString(9, employee.getStatus().name());
    }

    private List<Employee> mapEmployees(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(mapEmployee(resultSet));
        }
        return employees;
    }

    private Employee mapEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getInt("employee_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getString("department"),
                resultSet.getString("job_title"),
                resultSet.getBigDecimal("salary"),
                resultSet.getDate("hire_date").toLocalDate(),
                EmployeeStatus.valueOf(resultSet.getString("status"))
        );
    }
}
