package com.abhijit.ems.ui;

import com.abhijit.ems.exception.DataAccessException;
import com.abhijit.ems.exception.EmployeeNotFoundException;
import com.abhijit.ems.exception.ValidationException;
import com.abhijit.ems.model.Employee;
import com.abhijit.ems.model.EmployeeStatus;
import com.abhijit.ems.service.EmployeeService;
import com.abhijit.ems.util.InputReader;
import com.abhijit.ems.util.TablePrinter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class ConsoleMenu {
    private final EmployeeService employeeService;
    private final InputReader inputReader = new InputReader();

    public ConsoleMenu(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void start() {
        boolean running = true;
        printHeader();

        while (running) {
            printMenu();
            int choice = inputReader.readIntInRange("Choose option: ", 1, 8);

            try {
                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        listEmployees();
                        break;
                    case 3:
                        findEmployeeById();
                        break;
                    case 4:
                        searchEmployees();
                        break;
                    case 5:
                        updateEmployee();
                        break;
                    case 6:
                        deleteEmployee();
                        break;
                    case 7:
                        showDepartmentSalarySummary();
                        break;
                    case 8:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (ValidationException | EmployeeNotFoundException exception) {
                System.out.println("Error: " + exception.getMessage());
            } catch (DataAccessException exception) {
                System.out.println("Database error: " + exception.getMessage());
                if (exception.getCause() != null) {
                    System.out.println("Details: " + exception.getCause().getMessage());
                }
            }

            if (running) {
                inputReader.waitForEnter();
            }
        }

        System.out.println("Thank you for using Employee Management System.");
    }

    private void printHeader() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       Employee Management System       ");
        System.out.println("========================================");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Add employee");
        System.out.println("2. View all employees");
        System.out.println("3. Find employee by ID");
        System.out.println("4. Search employees");
        System.out.println("5. Update employee");
        System.out.println("6. Delete employee");
        System.out.println("7. Department salary summary");
        System.out.println("8. Exit");
    }

    private void addEmployee() {
        Employee employee = readNewEmployee();
        Employee createdEmployee = employeeService.addEmployee(employee);
        System.out.println("Employee added successfully with ID: " + createdEmployee.getEmployeeId());
    }

    private void listEmployees() {
        TablePrinter.printEmployees(employeeService.listEmployees());
    }

    private void findEmployeeById() {
        int employeeId = inputReader.readInt("Employee ID: ");
        Employee employee = employeeService.getEmployeeById(employeeId);
        TablePrinter.printEmployees(Collections.singletonList(employee));
    }

    private void searchEmployees() {
        String keyword = inputReader.readRequiredString("Search by name, department, or job title: ");
        TablePrinter.printEmployees(employeeService.searchEmployees(keyword));
    }

    private void updateEmployee() {
        int employeeId = inputReader.readInt("Employee ID to update: ");
        Employee existingEmployee = employeeService.getEmployeeById(employeeId);
        System.out.println("Leave a field blank to keep the current value.");

        Employee updatedEmployee = new Employee(
                existingEmployee.getEmployeeId(),
                inputReader.readOptionalString("First name", existingEmployee.getFirstName()),
                inputReader.readOptionalString("Last name", existingEmployee.getLastName()),
                inputReader.readOptionalString("Email", existingEmployee.getEmail()),
                inputReader.readOptionalString("Phone", existingEmployee.getPhone()),
                inputReader.readOptionalString("Department", existingEmployee.getDepartment()),
                inputReader.readOptionalString("Job title", existingEmployee.getJobTitle()),
                inputReader.readOptionalBigDecimal("Salary", existingEmployee.getSalary()),
                inputReader.readOptionalLocalDate("Hire date (yyyy-mm-dd)", existingEmployee.getHireDate()),
                inputReader.readOptionalStatus("Status (ACTIVE/INACTIVE)", existingEmployee.getStatus())
        );

        Employee savedEmployee = employeeService.updateEmployee(updatedEmployee);
        System.out.println("Employee updated successfully.");
        TablePrinter.printEmployees(Collections.singletonList(savedEmployee));
    }

    private void deleteEmployee() {
        int employeeId = inputReader.readInt("Employee ID to delete: ");
        Employee employee = employeeService.getEmployeeById(employeeId);
        TablePrinter.printEmployees(Collections.singletonList(employee));

        if (inputReader.confirm("Delete this employee")) {
            employeeService.deleteEmployee(employeeId);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private void showDepartmentSalarySummary() {
        TablePrinter.printDepartmentSalarySummary(employeeService.getDepartmentSalarySummary());
    }

    private Employee readNewEmployee() {
        String firstName = inputReader.readRequiredString("First name: ");
        String lastName = inputReader.readRequiredString("Last name: ");
        String email = inputReader.readRequiredString("Email: ");
        String phone = inputReader.readRequiredString("Phone: ");
        String department = inputReader.readRequiredString("Department: ");
        String jobTitle = inputReader.readRequiredString("Job title: ");
        BigDecimal salary = inputReader.readBigDecimal("Salary: ");
        LocalDate hireDate = inputReader.readLocalDate("Hire date");
        EmployeeStatus status = inputReader.readStatus("Status");

        return new Employee(0, firstName, lastName, email, phone, department, jobTitle, salary, hireDate, status);
    }
}
