package edu.ucalgary.oop;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Read the properties file
                Properties props = new Properties();
                InputStream input = DatabaseManager.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");
                props.load(input);

                String url  = props.getProperty("db.url");
                String user = props.getProperty("db.username");
                String pass = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, pass);

            } catch (IOException e) {
                throw new SQLException("Could not load db.properties: " + e.getMessage());
            }
        }
        return connection;
    }
}