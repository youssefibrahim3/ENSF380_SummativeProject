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
                4 - Manage Inquiries
                5 - Manage Medical Records
                6 - Manage Relationships
                    """);
            int choice = Integer.parseInt(scanner.nextLine());

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
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- VICTIM MENU --
                    0. Back
                    1. Add Victim 
                    2. Modify Victim
                    3. Delete Victim (Soft)
                    4. Delete Victim (Hard)
                    5. Manage Skills
                    6. View Victims
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }

    private void manageSupplies()
    {
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- VICTIM MENU --
                    0. Back
                    1. Add Victim 
                    2. Modify Victim
                    3. Delete Victim (Soft)
                    4. Delete Victim (Hard)
                    5. Manage Skills
                    6. View Victims
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }

    private void manageLocations()
    {
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- VICTIM MENU --
                    0. Back
                    1. Add Victim 
                    2. Modify Victim
                    3. Delete Victim (Soft)
                    4. Delete Victim (Hard)
                    5. Manage Skills
                    6. View Victims
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }

    private void manageInquiries()
    {
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- VICTIM MENU --
                    0. Back
                    1. Add Victim 
                    2. Modify Victim
                    3. Delete Victim (Soft)
                    4. Delete Victim (Hard)
                    5. Manage Skills
                    6. View Victims
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }

    private void manageMedicalRecords()
    {
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- VICTIM MENU --
                    0. Back
                    1. Add Victim 
                    2. Modify Victim
                    3. Delete Victim (Soft)
                    4. Delete Victim (Hard)
                    5. Manage Skills
                    6. View Victims
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }

    private void manageRelationships()
    {
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- VICTIM MENU --
                    0. Back
                    1. Add Victim 
                    2. Modify Victim
                    3. Delete Victim (Soft)
                    4. Delete Victim (Hard)
                    5. Manage Skills
                    6. View Victims
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }


}
