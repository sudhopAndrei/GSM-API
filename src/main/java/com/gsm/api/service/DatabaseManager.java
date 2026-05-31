package com.gsm.api.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; // sau /XEPDB1
    private static final String USER = "USERPAOJ";
    private static final String PASSWORD = "a1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}