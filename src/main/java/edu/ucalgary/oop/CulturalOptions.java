package edu.ucalgary.oop;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class CulturalOptions implements Serializable {
    public static final long serialVersionUID = 1L;
    private HashMap<String, Set<String>> accommodations;

    /**
     * Constructs a new CulturalOptions object from the given parameters
     * 
     * @param accommodations A hashmap of accomodations for the person
     */
    public CulturalOptions(HashMap<String, Set<String>> accommodations) {
        this.accommodations = accommodations;
    }

    /**
     * Gets the accomodations from this CulturalOptions instance
     * 
     * @return A hashmap of the accomodations
     */
    public HashMap<String, Set<String>> getAccommodations() {
        return accommodations;
    }
}