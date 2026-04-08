package edu.ucalgary.oop;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class VictimController {
    private VictimDAO victimDAO;
    private Scanner scanner;

    public VictimController(VictimDAO victimDAO, Scanner scanner)
    {
        this.victimDAO = victimDAO;
        this.scanner = scanner;
    }

    private void viewVictims() 
    {
        List<DisasterVictim> victims = victimDAO.getAll();
        if (victims.isEmpty()) 
        { 
            System.out.println("No victims found."); 
            return; 
        }
        System.out.println("\n-- Victims --");
        for (int i = 0; i < victims.size(); i++) {
            DisasterVictim v = victims.get(i);
            System.out.println((i+1) + ". [ID:" + v.getId() + "] " + v.getFirstName() + " " + v.getLastName());
        }
        System.out.println();
    }

    private DisasterVictim pickVictim(String prompt) 
    {
        List<DisasterVictim> victims = victimDAO.getAll();
        if (victims.isEmpty()) 
        { 
            System.out.println("No victims in system."); 
            return null; 
        }
        System.out.println(prompt);
        for (int i = 0; i < victims.size(); i++) {
            DisasterVictim v = victims.get(i);
            System.out.println((i+1) + ". " + v.getFirstName() + " " + v.getLastName());
        }
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < victims.size()) return victims.get(choice);
            System.out.println("Invalid selection.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
        }
        return null;
    }

    private Location pickLocation(List<Location> locations, String prompt) 
    {
        if (locations.isEmpty()) 
        { 
            System.out.println("No locations in system."); 
            return null; 
        
        }
        System.out.println(prompt);
        for (int i = 0; i < locations.size(); i++) {
            System.out.println((i+1) + ". " + locations.get(i).getName());
        }
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < locations.size()) return locations.get(choice);
            System.out.println("Invalid selection.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
        }
        return null;
    }

    public void addVictim(List<Location> locations) 
    {
        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();
        if (firstName.isEmpty()) { System.out.println("First name cannot be empty."); return; }

        System.out.print("Last name (or press Enter to skip): ");
        String lastName = scanner.nextLine().trim();

        // Gender
        System.out.println("Gender: 1=Man, 2=Woman, 3=Boy, 4=Girl, 5=Non-binary person, 6=Please specify");
        String gender = null;
        String[] genderOptions = {"man", "woman", "boy", "girl", "non-binary person", "please specify"};
        try {
            int g = Integer.parseInt(scanner.nextLine()) - 1;
            if (g >= 0 && g < genderOptions.length) gender = genderOptions[g];
            else { System.out.println("Invalid gender choice."); return; }
        } catch (NumberFormatException e) { System.out.println("Please enter a number."); return; }

        // Location
        Location loc = pickLocation(locations, "Select location:");
        if (loc == null) return;

        // Age or DOB (Feature 5)
        System.out.println("Enter: 1 for Date of Birth, 2 for Approximate Age");
        DisasterVictim victim;
        try {
            int ageChoice = Integer.parseInt(scanner.nextLine());
            if (ageChoice == 1) {
                System.out.print("Date of birth (YYYY-MM-DD): ");
                LocalDate dob = LocalDate.parse(scanner.nextLine().trim());
                victim = new DisasterVictim(firstName, LocalDate.now(), dob);
            } else if (ageChoice == 2) {
                System.out.print("Approximate age: ");
                int age = Integer.parseInt(scanner.nextLine().trim());
                victim = new DisasterVictim(firstName, LocalDate.now(), age);
            } else {
                System.out.println("Invalid choice."); return;
            }
        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage()); return;
        }

        victim.setLastName(lastName.isEmpty() ? null : lastName);
        victim.setLocationId(loc.getId());

        try { victim.setGender(gender); }
        catch (IllegalArgumentException e) { System.out.println("Invalid gender: " + e.getMessage()); return; }

        // Cultural requirements (Feature 7)
        System.out.println("Set cultural requirements? (y/n)");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            setCulturalRequirements(victim);
        }

        if (victimDAO.insert(victim)) {
            // Save cultural requirements to DB
            for (String category : victim.getRequirements().keySet()) {
                victimDAO.insertCulturalRequirement(victim.getId(), category,
                    victim.getRequirements().get(category));
            }
            System.out.println("Victim added successfully with ID: " + victim.getId());
        } else {
            System.out.println("Failed to add victim.");
        }
    }

}
