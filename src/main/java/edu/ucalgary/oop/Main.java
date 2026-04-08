package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Main entry point for the program. Accesses ConsoleUI
 *
 * 
 * @author Youssef Ibrahim
 * @version 2.0
 * @since 2026-04-02
 */
public class Main {
    public static void main(String args[])
    {
        RequirementLoader loader = new RequirementLoader();
        try {
            Connection conn = DatabaseManager.getConnection();
            VictimDAO victimDAO = new VictimDAO(conn);
            SupplyDAO supplyDAO = new SupplyDAO(conn);
            LocationDAO locationDAO = new LocationDAO(conn);
            InquiryDAO inquiryDAO = new InquiryDAO(conn);

            ConsoleUI consoleUI = new ConsoleUI(victimDAO, supplyDAO, locationDAO, inquiryDAO, loader);
            consoleUI.start(); //MVC
        } catch (SQLException e) {
            ErrorLogger.log(e);
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }
}
