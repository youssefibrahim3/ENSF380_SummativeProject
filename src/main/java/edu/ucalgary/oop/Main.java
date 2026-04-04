/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;
import java.util.Scanner;

public class Main {

    // DatabaseManager service = new DatabaseManager();

    private static void manageVictims(Scanner scanner, ReliefService service)
    {
        boolean using = true;
        while (using)
        {
            // ADD CHOOSING BETWEEN ENTERING APPROXIMATE AGE AND BIRTHDATE WHEN ADDING ONE!
            // FOR MODIFY THERE IS A LOT OF STUFF! 

            //Allocation of supplies should not include expired supples in inventory list
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
            switch (choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:

                    break;
                default:
                    System.out.println("Unrecognized input. Please enter one of the above inputs.");
                    break;
            }
        }
    }

    private static void manageSupplies(Scanner scanner, ReliefService service)
    { // When opening the menu, warn of ALL expired items currently in inventory.
        boolean using = true;
        while (using)
        {
        System.out.println("""
                -- SUPPLIES MENU --
                1. Add Supply
                2. Modify Supply
                3. Delete Supply
                4. View Supplies
                """);
        }
    }

    private static void manageLocations(Scanner scanner, ReliefService service)
    {
        //note to self: have it print out a list of the locations

        boolean using = true;
        while (using)
        {
        System.out.println("""
                -- LOCATION MENU --
                1. Add Location
                2. Modify Location
                3. Delete Location
                """);
        }
    }

    
    public static void main(String args[])
    {
        Scanner scanner = new Scanner(System.in);

        boolean using = true;

        System.out.println("-- Disaster Relief Management System --");
        while (using)
        { //inquiries, supplies, locations, medical records, relationships, etc
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
                    manageVictims(scanner, null);
                    break;
                case 2:
                    manageSupplies(scanner, null);
                    break;
                case 3:
                    manageLocations(scanner, null);
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


}
