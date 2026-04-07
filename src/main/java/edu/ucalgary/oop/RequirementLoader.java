package edu.ucalgary.oop;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class RequirementLoader {
    private static final String FILE_PATH = "src/main/resources/available_requirements.ser";
    private CulturalOptions options;

    // Call this at program startup — exits if file not found (as required)
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
            System.exit(1);  // features doc says exit if file not found
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading available_requirements.ser: " + e.getMessage());
            System.exit(1);
        }
    }

    // Returns all requirement types and their options
    // e.g. "dietary restrictions" -> {"halal", "kosher", "vegetarian"}
    public HashMap<String, Set<String>> getAccommodations() {
        return options.getAccommodations();
    }

    // Returns the valid options for a specific requirement type
    public Set<String> getOptionsForType(String requirementType) {
        return options.getAccommodations().get(requirementType);
    }

    // Returns all requirement type names (e.g. "dietary restrictions", "safe-space requirements")
    public Set<String> getRequirementTypes() {
        return options.getAccommodations().keySet();
    }
}