/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;
import java.io.Console;
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
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.start();
    }


}
