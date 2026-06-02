package com.abhijit.ems.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConfig {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/employee_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private final String url;
    private final String user;
    private final String password;

    private DatabaseConfig(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static DatabaseConfig load() {
        Properties properties = new Properties();

        try (InputStream inputStream = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load database properties.", exception);
        }

        String url = firstNonBlank(System.getenv("EMS_DB_URL"), properties.getProperty("db.url"), DEFAULT_URL);
        String user = firstNonBlank(System.getenv("EMS_DB_USER"), properties.getProperty("db.user"), DEFAULT_USER);
        String password = firstNonBlank(
                System.getenv("EMS_DB_PASSWORD"),
                properties.getProperty("db.password"),
                DEFAULT_PASSWORD
        );

        return new DatabaseConfig(url, user, password);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
        }
        return "";
    }
}
