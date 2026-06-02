package com.abhijit.ems;

import com.abhijit.ems.config.DatabaseConfig;
import com.abhijit.ems.dao.EmployeeDao;
import com.abhijit.ems.dao.JdbcEmployeeDao;
import com.abhijit.ems.service.EmployeeService;
import com.abhijit.ems.ui.ConsoleMenu;

public class App {
    public static void main(String[] args) {
        DatabaseConfig databaseConfig = DatabaseConfig.load();
        EmployeeDao employeeDao = new JdbcEmployeeDao(databaseConfig);
        EmployeeService employeeService = new EmployeeService(employeeDao);
        new ConsoleMenu(employeeService).start();
    }
}
