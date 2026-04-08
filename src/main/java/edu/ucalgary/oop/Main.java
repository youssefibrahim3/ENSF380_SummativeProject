package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.SQLException;

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
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }
}
