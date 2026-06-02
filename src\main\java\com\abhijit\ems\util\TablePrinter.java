package com.abhijit.ems.util;

import com.abhijit.ems.model.DepartmentSalarySummary;
import com.abhijit.ems.model.Employee;

import java.math.BigDecimal;
import java.util.List;

public final class TablePrinter {
    private TablePrinter() {
    }

    public static void printEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println(line(133));
        System.out.printf(
                "%-5s %-18s %-25s %-15s %-18s %-20s %-12s %-12s %-10s%n",
                "ID", "Name", "Email", "Phone", "Department", "Job Title", "Salary", "Hire Date", "Status"
        );
        System.out.println(line(133));
        for (Employee employee : employees) {
            System.out.printf(
                    "%-5d %-18s %-25s %-15s %-18s %-20s %-12s %-12s %-10s%n",
                    employee.getEmployeeId(),
                    fit(employee.getFullName(), 18),
                    fit(employee.getEmail(), 25),
                    fit(employee.getPhone(), 15),
                    fit(employee.getDepartment(), 18),
                    fit(employee.getJobTitle(), 20),
                    formatMoney(employee.getSalary()),
                    employee.getHireDate(),
                    employee.getStatus()
            );
        }
        System.out.println(line(133));
    }

    public static void printDepartmentSalarySummary(List<DepartmentSalarySummary> summaries) {
        if (summaries.isEmpty()) {
            System.out.println("No salary summary found.");
            return;
        }

        System.out.println(line(70));
        System.out.printf("%-22s %-12s %-15s %-15s%n", "Department", "Employees", "Avg Salary", "Total Salary");
        System.out.println(line(70));
        for (DepartmentSalarySummary summary : summaries) {
            System.out.printf(
                    "%-22s %-12d %-15s %-15s%n",
                    fit(summary.getDepartment(), 22),
                    summary.getEmployeeCount(),
                    formatMoney(summary.getAverageSalary()),
                    formatMoney(summary.getTotalSalary())
            );
        }
        System.out.println(line(70));
    }

    private static String fit(String value, int width) {
        String safeValue = value == null ? "" : value;
        if (safeValue.length() <= width) {
            return safeValue;
        }
        if (width <= 3) {
            return safeValue.substring(0, width);
        }
        return safeValue.substring(0, width - 3) + "...";
    }

    private static String formatMoney(BigDecimal value) {
        return value == null ? "0.00" : value.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
    }

    private static String line(int length) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < length; index++) {
            builder.append('-');
        }
        return builder.toString();
    }
}
