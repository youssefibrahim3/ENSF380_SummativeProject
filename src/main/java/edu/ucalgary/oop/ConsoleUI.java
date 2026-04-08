package edu.ucalgary.oop;

import java.util.Scanner;

public class ConsoleUI {
    //Should I create a DAO object directly, or use stuff like "SupplyController" that interact with the DAO within them?

    private Scanner scanner = new Scanner(System.in);
    private final VictimDAO victimDAO;
    private final SupplyDAO supplyDAO;
    private final LocationDAO locationDAO;
    private final InquiryDAO inquiryDAO;
    private final RequirementLoader requirementLoader;

    public ConsoleUI(VictimDAO victimDAO, SupplyDAO supplyDAO, LocationDAO locationDAO,
                    InquiryDAO inquiryDAO, RequirementLoader requirementLoader)
    {
        this.victimDAO = victimDAO;
        this.supplyDAO = supplyDAO;
        this.locationDAO = locationDAO;
        this.inquiryDAO = inquiryDAO;
        this.requirementLoader = requirementLoader;
    }

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
                    manageInquiries();
                    break;
                case 5:
                    manageMedicalRecords();
                    break;
                case 6:
                    manageRelationships();
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
