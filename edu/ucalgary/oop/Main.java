/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;
import java.util.Scanner;

public class Main {

    public static void main(String args[])
    {
        Scanner scanner = new Scanner(System.in);
        boolean using = true;

        System.out.println("-- RESCUE PROGRAM THING --");
        while (using)
        { //inquiries, supplies, locations, medical records, relationships, etc
            System.out.println("""
                Please enter an option:
                0 - Exit program
                1 - Add Disaster Victim
                2 - Modify Disaster Victim

                3 - Add Location
                4 - Modify Location

                5 - Add Medical Record
                6 - Modify Medical Record

                7 - Add Relationship
                8 - Modify Relationship

                9 - Add Inquiry
                10 - Modify Inquiry
                    """);
            int choice = scanner.nextInt();

            //add/modify everythang
            switch (choice) {
                case 0:
                    System.out.println("Exiting...\n");
                    using = false;
                    break;
                case 1:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.\n");
                    break;
            }

        }
        scanner.close();
    }
}
