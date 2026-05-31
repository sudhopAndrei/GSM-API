package com.gsm.api.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(DatabaseManager.class.getClassLoader().getResourceAsStream("database.properties"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DatabaseManager() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password")
        );
    }
}