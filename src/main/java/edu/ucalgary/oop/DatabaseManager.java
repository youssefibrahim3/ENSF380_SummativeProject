package edu.ucalgary.oop;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost/ensf380project";
    private static final String USER = "oop";
    private static final String PASSWORD = "ucalgary";
    
    private static Connection connection = null;

    private DatabaseManager() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}