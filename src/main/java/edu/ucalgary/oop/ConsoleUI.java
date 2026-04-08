package edu.ucalgary.oop;

import java.util.Scanner;

public class ConsoleUI {
    //private SupplyController supplyController;
    //put the DAO calls inside of the controllers!
    private Scanner scanner = new Scanner(System.in);

    /**
     * Begins the console interface. 
     * Prompts the user for their input, then goes to that interface.
     */
    public void start() {
        boolean using = true;
        System.out.println("-- Disaster Relief Management System --");
        while (using)
        {
            System.out.println("""
                Please enter an option:
                0 - Exit program
                
                1 - Manage Victims
                2 - Manage Supplies
                3 - Manage Locations
                4 - Manage Medical Records
                5 - Manage Relationships
                6 - Manage Inquiries
                    """);
            int choice = Integer.parseInt(scanner.nextLine());

            //add/modify everythang
            switch (choice) {
                case 0:
                    System.out.println("Exiting...\n");
                    using = false;
                    break;
                case 1:
                    manageVictims();
                    break;
                case 2:
                    manageSupplies();
                    break;
                case 3:
                    manageLocations();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.\n");
                    break;
            }

        }
        scanner.close();
    }

    private void manageVictims()
    {

    }

    private void manageSupplies()
    {

    }

    private void manageLocations()
    {

    }

    private void manageInquiries()
    {
        
    }
}
