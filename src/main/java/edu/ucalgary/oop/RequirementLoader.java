package edu.ucalgary.oop;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class RequirementLoader {
    private static final String FILE_PATH = "src/main/resources/available_requirements.ser";
    private CulturalOptions options;

    /**
     * Constructs a new RequirementLoader. Exits the program if the file to load was not found
     */
    public RequirementLoader() {
        try {
            FileInputStream fileIn = new FileInputStream(FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.options = (CulturalOptions) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Cultural requirements loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: available_requirements.ser not found in resources folder.");
            System.exit(1);  // exit if file not found
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading available_requirements.ser: " + e.getMessage());
            System.exit(1);
        }
    }

    /** 
     * Gets all requirement types and their options.
     * 
     * @return A HashMap<String, Set<String>> containing the requirement types and their options
     */
    public HashMap<String, Set<String>> getAccommodations() {
        return options.getAccommodations();
    }

    /** 
     * Gets the valid options for a specific requirement type.
     * 
     * @param requirementType The requirement type to view options for
     * @return A Set<String> containing all of the options
     */
    public Set<String> getOptionsForType(String requirementType) {
        return options.getAccommodations().get(requirementType);
    }

    /** 
     * Gets all requirement type names.
     * 
     * @return A Set<String> containing names of all of the requirement types
     */
    public Set<String> getRequirementTypes() {
        return options.getAccommodations().keySet();
    }
}