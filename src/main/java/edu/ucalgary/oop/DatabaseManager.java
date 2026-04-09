package edu.ucalgary.oop;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Class for creating connection to database
 * 
 * @author Youssef Ibrahim
 * @version 2.0
 * @since 2026-04-03
 */

public class DatabaseManager {
    private static Connection connection = null;

    /**
     * If there is no current open connection yet, reads from db.properties and creates a new connection using these
     * credentials.
     * 
     * @return The Connection object that represents the connection to the database. 
     * @throws SQLException if an error is occured when loading from db.properties
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Read the properties file
                Properties props = new Properties();
                InputStream input = new FileInputStream("src/main/resources/app.properties");
                props.load(input);

                String url  = props.getProperty("db.url");
                String user = props.getProperty("db.username");
                String pass = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, pass);

            } catch (IOException e) {
                throw new SQLException("Could not load app.properties: " + e.getMessage());
            }
        }
        return connection;
    }
}