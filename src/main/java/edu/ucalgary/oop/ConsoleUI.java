package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Class handling main console UI interactions
 *
 * 
 * @author Youssef Ibrahim
 * @version 2.0
 * @since 2026-04-03
 */
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

    //Victims

    private void viewVictims() 
    {
        List<DisasterVictim> victims = victimDAO.getAll();
        if (victims.isEmpty()) { 
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

    private DisasterVictim pickVictim(String prompt) {
        List<DisasterVictim> victims = victimDAO.getAll();
        if (victims.isEmpty()) { System.out.println("No victims in system."); return null; }
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

    private Location pickLocation(String prompt) {
        List<Location> locations = locationDAO.getAll();
        if (locations.isEmpty()) { 
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

    private void addVictim() 
    {
        System.out.print("First name: ");
        String firstName = scanner.nextLine().trim();
        if (firstName.isEmpty()) { 
            System.out.println("First name cannot be empty."); 
            return; 
        }

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
        Location loc = pickLocation("Select location:");
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

    private void setCulturalRequirements(DisasterVictim victim) 
    {
        // Show available requirement types from .ser file
        for (String type : requirementLoader.getRequirementTypes()) {
            System.out.println("Set requirement for '" + type + "'? (y/n)");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) continue;

            List<String> options = new java.util.ArrayList<>(requirementLoader.getOptionsForType(type));
            System.out.println("Choose option:");
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i+1) + ". " + options.get(i));
            }
            try {
                int choice = Integer.parseInt(scanner.nextLine()) - 1;
                if (choice >= 0 && choice < options.size()) {
                    victim.setRequirement(type, options.get(choice));
                } else {
                    System.out.println("Invalid choice, skipping.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, skipping.");
            }
        }
    }

    private void modifyVictim() 
    {
        DisasterVictim victim = pickVictim("Select victim to modify:");
        if (victim == null) return;

        System.out.println("Modifying: " + victim.getFirstName() + " " + victim.getLastName());
        System.out.println("""
            What would you like to modify?
            1. First name
            2. Last name
            3. Gender
            4. Location
            5. Update approximate age
            6. Replace approximate age with date of birth
            7. Cultural requirements
            0. Cancel
            """);

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.print("New first name: ");
                    victim.setFirstName(scanner.nextLine().trim());
                    victimDAO.update(victim);
                    break;
                case 2:
                    System.out.print("New last name: ");
                    victim.setLastName(scanner.nextLine().trim());
                    victimDAO.update(victim);
                    break;
                case 3:
                    System.out.println("Gender: 1=Man, 2=Woman, 3=Boy, 4=Girl, 5=Non-binary person");
                    String[] genderOptions = {"man", "woman", "boy", "girl", "non-binary person"};
                    int g = Integer.parseInt(scanner.nextLine()) - 1;
                    if (g >= 0 && g < genderOptions.length) {
                        try {
                            victim.setGender(genderOptions[g]);
                            victimDAO.update(victim);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid gender for this victim: " + e.getMessage());
                        }
                    }
                    break;
                case 4:
                    Location loc = pickLocation("Select new location:");
                    if (loc != null) {
                        victim.setLocationId(loc.getId());
                        victimDAO.update(victim);
                    }
                    break;
                case 5:
                    // Feature 5 — only if no DOB
                    if (victim.getDateOfBirth() != null) {
                        System.out.println("Cannot set approximate age — victim already has a date of birth.");
                    } else {
                        System.out.print("New approximate age: ");
                        int age = Integer.parseInt(scanner.nextLine().trim());
                        victimDAO.updateApproximateAge(victim.getId(), age);
                    }
                    break;
                case 6:
                    // Feature 5 — replace approx age with real DOB
                    System.out.print("Date of birth (YYYY-MM-DD): ");
                    LocalDate dob = LocalDate.parse(scanner.nextLine().trim());
                    victimDAO.replaceAgeWithDOB(victim.getId(), dob);
                    break;
                case 7:
                    setCulturalRequirements(victim);
                    for (String category : victim.getRequirements().keySet()) {
                        victimDAO.insertCulturalRequirement(victim.getId(), category,
                            victim.getRequirements().get(category));
                    }
                    System.out.println("Requirements updated.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    private void softDeleteVictim() 
    {
        DisasterVictim victim = pickVictim("Select victim to soft delete:");
        if (victim == null) return;
        System.out.println("WARNING: This will hide " + victim.getFirstName() +
            " " + victim.getLastName() + " from the system. Continue? (y/n)");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            victimDAO.softDelete(victim.getId());
            System.out.println("Victim soft deleted.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    private void hardDeleteVictim() 
    {
        DisasterVictim victim = pickVictim("Select victim to permanently delete:");
        if (victim == null) return;
        System.out.println("WARNING: This will PERMANENTLY delete " + victim.getFirstName() +
            " " + victim.getLastName() + " and ALL their records. This cannot be undone.");
        System.out.println("Type 'DELETE' to confirm:");
        if (scanner.nextLine().trim().equals("DELETE")) {
            victimDAO.delete(victim.getId());
            System.out.println("Victim permanently deleted.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    private void manageSkills() 
    {
        DisasterVictim victim = pickVictim("Select victim to manage skills:");
        if (victim == null) return;

        boolean using = true;
        while (using) {
            System.out.println("\n-- Skills for " + victim.getFirstName() + " --");
            List<Skill> skills = victim.getSkills();
            if (skills.isEmpty()) System.out.println("No skills registered.");
            else for (int i = 0; i < skills.size(); i++) {
                Skill s = skills.get(i);
                System.out.println((i+1) + ". [" + s.getSkillCategory() + "] " +
                    s.getProficiencyLevel());
            }

            System.out.println("""
                1. Add skill
                2. Remove skill
                0. Back
                """);

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 0: using = false; break;
                    case 1: addSkillToVictim(victim); break;
                    case 2: removeSkillFromVictim(victim); break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private void addSkillToVictim(DisasterVictim victim) {
        System.out.println("Skill category:\n1 - Medical\n2 -Language\n3 - Trade\n");
        try {
            int cat = Integer.parseInt(scanner.nextLine());
            System.out.println("Proficiency level:\n1 - Beginner\n2 -Intermediate\n3 - Advanced\n");
            int profChoice = Integer.parseInt(scanner.nextLine());
            ProficiencyLevel[] levels = {ProficiencyLevel.BEGINNER, ProficiencyLevel.INTERMEDIATE, ProficiencyLevel.ADVANCED};
            if (profChoice < 1 || profChoice > 3) { 
                System.out.println("Invalid proficiency choice."); 
                return; 
            }
            ProficiencyLevel level = levels[profChoice - 1];

            Skill skill = null;
            if (cat == 1) {
                System.out.println("Certification: 1=First Aid, 2=Counseling, 3=Nursing, 4=Doctor");
                int certChoice = Integer.parseInt(scanner.nextLine());
                MedicalSkill.Certification[] certs = MedicalSkill.Certification.values();
                if (certChoice < 1 || certChoice > 4) { System.out.println("Invalid."); return; }
                System.out.print("Expiry date (YYYY-MM-DD): ");
                LocalDate expiry = LocalDate.parse(scanner.nextLine().trim());
                skill = new MedicalSkill(level, certs[certChoice - 1], expiry);
            } else if (cat == 2) {
                System.out.print("Language: ");
                String lang = scanner.nextLine().trim();
                System.out.println("Capabilities: 1=Read/Write, 2=Speak/Listen, 3=Both");
                int capChoice = Integer.parseInt(scanner.nextLine());
                LanguageSkill.Capabilities[] caps;
                if (capChoice == 1) caps = new LanguageSkill.Capabilities[]{LanguageSkill.Capabilities.READ_WRITE};
                else if (capChoice == 2) caps = new LanguageSkill.Capabilities[]{LanguageSkill.Capabilities.SPEAK_LISTEN};
                else caps = new LanguageSkill.Capabilities[]{LanguageSkill.Capabilities.READ_WRITE, LanguageSkill.Capabilities.SPEAK_LISTEN};
                skill = new LanguageSkill(level, lang, caps);
            } else if (cat == 3) {
                System.out.println("Trade type: 1=Carpentry, 2=Plumbing, 3=Electricity");
                int typeChoice = Integer.parseInt(scanner.nextLine());
                TradeSkill.SkillType[] types = TradeSkill.SkillType.values();
                if (typeChoice < 1 || typeChoice > 3) { System.out.println("Invalid."); return; }
                skill = new TradeSkill(level, types[typeChoice - 1]);
            } else {
                System.out.println("Invalid category."); return;
            }

            try {
                victim.registerSkill(skill);
                victimDAO.insertSkill(victim.getId(), skill);
                System.out.println("Skill added.");
            } catch (IllegalArgumentException e) {
                System.out.println("Cannot add skill: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    private void removeSkillFromVictim(DisasterVictim victim) {
        List<Skill> skills = victim.getSkills();
        if (skills.isEmpty()) { System.out.println("No skills to remove."); return; }
        System.out.println("Select skill to remove:");
        for (int i = 0; i < skills.size(); i++) {
            System.out.println((i+1) + ". [" + skills.get(i).getSkillCategory() + "] " +
                skills.get(i).getProficiencyLevel());
        }
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < skills.size()) {
                Skill s = skills.get(choice);
                victim.removeSkill(s);
                victimDAO.deleteSkill(victim.getId(), s);
                System.out.println("Skill removed.");
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
        }
    }

    private void searchBySkillCategory() {
        System.out.println("Category: 1=Medical, 2=Language, 3=Trade");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            String[] categories = {"medical", "language", "trade"};
            if (choice < 1 || choice > 3) { System.out.println("Invalid."); return; }
            List<DisasterVictim> results = victimDAO.getVictimsBySkillCategory(categories[choice - 1]);
            if (results.isEmpty()) { System.out.println("No victims found with this skill category."); return; }
            System.out.println("\n-- Results --");
            for (DisasterVictim v : results) {
                System.out.println("- " + v.getFirstName() + " " + v.getLastName() + " [ID:" + v.getId() + "]");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
        }
    }

    /**
     * Provides options regarding managing victims in the database.
     */
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
                    7. Search Victims by Skill Category
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    addVictim();
                    break;
                case 2:
                    modifyVictim();
                    break;
                case 3:
                    softDeleteVictim();
                    break;
                case 4:
                    hardDeleteVictim();
                    break;
                case 5:
                    manageSkills();
                    break;
                case 6:
                    viewVictims();
                    break;
                case 7:
                    searchBySkillCategory();
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input (0-7).");
            }
        }
    }

    //Supplies
    /**
     * Provides options regarding managing supplies in the database.
     */
    private void manageSupplies()
    {
        List<Supply> supplysList = supplyDAO.getAll();
        for (Supply s : supplysList)
        {
            if (s.isExpired())
            {
                System.out.println("WARNING: " + s.getType() + " is expired. (expired: " + s.getExpirationDate() + ")");
            }
        }
        
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- SUPPLIES MENU --
                    0. Back
                    1. Add Supply 
                    2. Allocate Supply to Victim
                    3. View Suplies
                    4. Delete Supply
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }

    //Locations
    /**
     * Provides options regarding managing locations in the database.
     */
    private void manageLocations()
    {
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- LOCATION MENU --
                    0. Back
                    1. Add Location
                    2. View Locations
                    3. Modify Location
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }

    //Inquiries

    /**
     * Provides options regarding managing inquiries in the database.
     */
    private void manageInquiries()
    {
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- INQUIRIES MENU --
                    0. Back
                    1. Add Inquiry
                    2. View Inquiries
                    3. Modify Inquiry
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }

    //Medical records

    /**
     * Provides options regarding managing medical records in the database.
     */
    private void manageMedicalRecords()
    {
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- MEDICAL RECORDS MENU --
                    0. Back
                    1. Add Medical Record
                    2. View Medical Records for Victim
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }

    //Relationships

    /**
     * Provides options regarding managing relationships in the database.
     */
    private void manageRelationships()
    {
        boolean using = true;
        while (using)
        {
            System.out.println("""
                    -- RELATIONSHIPS MENU --
                    0. Back
                    1. Add Relationship
                    2. View Relationships for Victim
                    """);

            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice)
            {
                case 0:
                    using = false;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Unrecognized input. Please enter a valid input.");
            }
        }
    }
}
