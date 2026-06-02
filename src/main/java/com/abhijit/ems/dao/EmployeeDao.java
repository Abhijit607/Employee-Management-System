package com.abhijit.ems.dao;

import com.abhijit.ems.model.DepartmentSalarySummary;
import com.abhijit.ems.model.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    Employee create(Employee employee) throws SQLException;

    Optional<Employee> findById(int employeeId) throws SQLException;

    List<Employee> findAll() throws SQLException;

    List<Employee> searchByNameOrDepartment(String keyword) throws SQLException;

    boolean update(Employee employee) throws SQLException;

    boolean deleteById(int employeeId) throws SQLException;

    boolean emailExists(String email, Integer excludingEmployeeId) throws SQLException;

    List<DepartmentSalarySummary> getDepartmentSalarySummary() throws SQLException;
}
