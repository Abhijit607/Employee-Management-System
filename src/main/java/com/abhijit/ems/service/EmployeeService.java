package com.abhijit.ems.service;

import com.abhijit.ems.dao.EmployeeDao;
import com.abhijit.ems.exception.DataAccessException;
import com.abhijit.ems.exception.EmployeeNotFoundException;
import com.abhijit.ems.exception.ValidationException;
import com.abhijit.ems.model.DepartmentSalarySummary;
import com.abhijit.ems.model.Employee;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeeService {
    private final EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Employee addEmployee(Employee employee) {
        validateEmployee(employee);

        try {
            if (employeeDao.emailExists(employee.getEmail(), null)) {
                throw new ValidationException("Email already exists: " + employee.getEmail());
            }
            return employeeDao.create(employee);
        } catch (SQLException exception) {
            throw new DataAccessException("Unable to add employee.", exception);
        }
    }

    public List<Employee> listEmployees() {
        try {
            return employeeDao.findAll();
        } catch (SQLException exception) {
            throw new DataAccessException("Unable to load employees.", exception);
        }
    }

    public Employee getEmployeeById(int employeeId) {
        try {
            return employeeDao.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        } catch (SQLException exception) {
            throw new DataAccessException("Unable to find employee.", exception);
        }
    }

    public List<Employee> searchEmployees(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ValidationException("Search keyword is required.");
        }

        try {
            return employeeDao.searchByNameOrDepartment(keyword.trim());
        } catch (SQLException exception) {
            throw new DataAccessException("Unable to search employees.", exception);
        }
    }

    public Employee updateEmployee(Employee employee) {
        if (employee.getEmployeeId() <= 0) {
            throw new ValidationException("Employee id is required for update.");
        }
        validateEmployee(employee);

        try {
            getEmployeeById(employee.getEmployeeId());
            if (employeeDao.emailExists(employee.getEmail(), employee.getEmployeeId())) {
                throw new ValidationException("Email already exists: " + employee.getEmail());
            }
            boolean updated = employeeDao.update(employee);
            if (!updated) {
                throw new EmployeeNotFoundException(employee.getEmployeeId());
            }
            return getEmployeeById(employee.getEmployeeId());
        } catch (SQLException exception) {
            throw new DataAccessException("Unable to update employee.", exception);
        }
    }

    public void deleteEmployee(int employeeId) {
        try {
            boolean deleted = employeeDao.deleteById(employeeId);
            if (!deleted) {
                throw new EmployeeNotFoundException(employeeId);
            }
        } catch (SQLException exception) {
            throw new DataAccessException("Unable to delete employee.", exception);
        }
    }

    public List<DepartmentSalarySummary> getDepartmentSalarySummary() {
        try {
            return employeeDao.getDepartmentSalarySummary();
        } catch (SQLException exception) {
            throw new DataAccessException("Unable to load salary summary.", exception);
        }
    }

    private void validateEmployee(Employee employee) {
        requireText(employee.getFirstName(), "First name");
        requireText(employee.getLastName(), "Last name");
        requireText(employee.getEmail(), "Email");
        requireText(employee.getDepartment(), "Department");
        requireText(employee.getJobTitle(), "Job title");

        if (!employee.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidationException("Email format is invalid.");
        }
        if (employee.getSalary() == null || employee.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Salary must be zero or greater.");
        }
        if (employee.getHireDate() == null || employee.getHireDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Hire date cannot be empty or in the future.");
        }
        if (employee.getStatus() == null) {
            throw new ValidationException("Status is required.");
        }
    }

    private void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required.");
        }
    }
}
