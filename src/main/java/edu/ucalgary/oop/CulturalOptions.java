package edu.ucalgary.oop;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Class representing cultural options/accomodations for a victim
 * 
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-04-05
 */

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