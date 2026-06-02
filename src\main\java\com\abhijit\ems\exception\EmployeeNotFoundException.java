package com.abhijit.ems.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(int employeeId) {
        super("Employee not found with id: " + employeeId);
    }
}
