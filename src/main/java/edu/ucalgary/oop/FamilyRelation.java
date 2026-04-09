package edu.ucalgary.oop;

/**
 * Class representing a (family) relation between two people.
 * 
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-04-02
 */

public class FamilyRelation {
    private DisasterVictim personOne;
    private String relationshipTo;
    private DisasterVictim personTwo;

    /**
     * Constructs a new FamilyRelation object with the specified attributes
     * 
     * @param personOne A DisasterVictim that represents the first person in the relationship
     * @param relationshipTo What the relationship between the two people are
     * @param personTwo A DisasterVictim that represents the second person in the relationship
     * @throws IllegalArgumentException
     */
    public FamilyRelation(DisasterVictim personOne, String relationshipTo, DisasterVictim personTwo) 
            throws IllegalArgumentException {
        if (personOne == null || personTwo == null) {
            throw new IllegalArgumentException("Persons in a family relation cannot be null");
        }
        this.personOne = personOne;
        this.relationshipTo = relationshipTo;
        this.personTwo = personTwo;
    }

    /**
     * Gets the first person in the relationship.
     * 
     * @return A DisasterVictim object representing the first person.
     */
    public DisasterVictim getPersonOne() {
        return personOne;
    }

    /**
     * Sets the first person in the relationship.
     * 
     * @param personOne A DisasterVictim that represents the first person in the relationship
     * @throws IllegalArgumentException if argument PersonOne is null
     */
    public void setPersonOne(DisasterVictim personOne) throws IllegalArgumentException {
        if (personOne == null) {
            throw new IllegalArgumentException("PersonOne cannot be null");
        }
        this.personOne = personOne;
    }

    /**
     * Gets the relationship between the two people.
     * 
     * @return The nature of the relationship between the two.
     */
    public String getRelationshipTo() {
        return relationshipTo;
    }

    /**
     * Sets the relationship between the two people.
     * 
     * @param relationshipTo The relationship between the two.
     */
    public void setRelationshipTo(String relationshipTo) {
        this.relationshipTo = relationshipTo;
    }
    
    /**
     * Gets the second person in the relationship.
     * 
     * @return A DisasterVictim object representing the second person.
     */
    public DisasterVictim getPersonTwo() {
        return personTwo;
    }

    /**
     * Sets the second person in the relationship.
     * 
     * @param personOne A DisasterVictim that represents the second person in the relationship
     * @throws IllegalArgumentException if argument PersonTwo is null
     */
    public void setPersonTwo(DisasterVictim personTwo) throws IllegalArgumentException {
        if (personTwo == null) {
            throw new IllegalArgumentException("PersonTwo cannot be null");
        }
        this.personTwo = personTwo;
    }
}
