package com.abhijit.ems.model;

import java.math.BigDecimal;

public class DepartmentSalarySummary {
    private final String department;
    private final int employeeCount;
    private final BigDecimal averageSalary;
    private final BigDecimal totalSalary;

    public DepartmentSalarySummary(
            String department,
            int employeeCount,
            BigDecimal averageSalary,
            BigDecimal totalSalary
    ) {
        this.department = department;
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
        this.totalSalary = totalSalary;
    }

    public String getDepartment() {
        return department;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public BigDecimal getTotalSalary() {
        return totalSalary;
    }
}
