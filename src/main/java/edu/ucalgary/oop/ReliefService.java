package edu.ucalgary.oop;
import java.time.LocalDate;

/**
 * Class representing a single ReliefService (inquiry) in the database
 * 
 * @author Youssef Ibrahim
 * @version 2.0
 * @since 2026-04-02
 */

public class ReliefService {
    private Inquirer inquirer;
    private DisasterVictim missingPerson;
    private LocalDate dateOfInquiry; 
    private String infoProvided;
    private Location lastKnownLocation;
    private int id;

    /**
     * Constructs a new ReliefService object with the given parameters.
     * 
     * @param inquirer The inquirer associated with this service
     * @param missingPerson The missing person associated with this service
     * @param dateOfInquiry The date of inquiry
     * @param infoProvided Info provided regarding this inquiry
     * @param lastKnownLocation The last known location of the missing person
     */
    public ReliefService(Inquirer inquirer, DisasterVictim missingPerson, LocalDate dateOfInquiry, String infoProvided, Location lastKnownLocation) {
        this.inquirer = inquirer;
        this.missingPerson = missingPerson;
        setDateOfInquiry(dateOfInquiry); // This will validate the date
        this.infoProvided = infoProvided;
        this.lastKnownLocation = lastKnownLocation;
    }

    public ReliefService()
    {

    }

    /** 
     * Gets the inquirer associated with this object.
     * 
     * @return The Inquirer associated with this inquiry.
     */
    public Inquirer getInquirer() {
        return inquirer;
    }

    /** 
     * Sets the inquirer associated with this object.
     * 
     * @param inquirer The new Inquirer to set to this ReliefService
     */
    public void setInquirer(Inquirer inquirer) {
        this.inquirer = inquirer;
    }

    /** 
     * Gets the associated missing person with this ReliefService.
     * 
     * @return A DisasterVictim represnting the missing person
     */
    public DisasterVictim getMissingPerson() {
        return missingPerson;
    }

    /** 
     * Sets the associated missing person with this ReliefService.
     * 
     * @param missingPerson A DisasterVictim representing the missing person
     */
    public void setMissingPerson(DisasterVictim missingPerson) {
        this.missingPerson = missingPerson;
    }

    /** 
     * Gets the date of inquiry.
     * 
     * @return A LocalDate representing the date of inquiry
     */
    public LocalDate getDateOfInquiry() {
        return dateOfInquiry;
    }

    /** 
     * Sets the date of inquiry.
     * 
     * @param dateOfInquiry A LocalDate representing the new date of inquiry.
     * @throws IllegalArgumentException if dateOfInquiry is null or in the future
     */
    public void setDateOfInquiry(LocalDate dateOfInquiry) throws IllegalArgumentException {
        if (dateOfInquiry == null) {
            throw new IllegalArgumentException("Date of inquiry cannot be null");
        }
        if (dateOfInquiry.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of inquiry cannot be in the future");
        }
        this.dateOfInquiry = dateOfInquiry;
    }

    /** 
     * Gets the info provided associated with this relief service.
     * 
     * @return A String containing the info provided
     */
    public String getInfoProvided() {
        return infoProvided;
    }

    /** 
     * Sets the info provided associated with this relief service.
     * 
     * @param infoProvided A String representing the new info
     */
    public void setInfoProvided(String infoProvided) {
        this.infoProvided = infoProvided;
    }

    /** 
     * Gets the last known location of the missing person.
     * 
     * @return A Location representing the last known location.
     */
    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    /** 
     * Sets the last known location of the missing person.
     * 
     * @param lastKnownLocation The new Location representing the last known location.
     */
    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }
    
    /** 
     * Gets all of the details associated with this ReliefService.
     * 
     * @return A String detailing the inquirer, missing person, date of inquiry,
     * info provided, and last known location
     */
    public String getLogDetails() {
       return "Inquirer: " + inquirer.getFirstName() + 
           ", Missing Person: " + missingPerson.getFirstName() + 
           ", Date of Inquiry: " + dateOfInquiry + 
           ", Info Provided: " + infoProvided + 
           ", Last Known Location: " + lastKnownLocation.getName();
    }

    /**
     * Gets the ID of this ReliefService.
     * 
     * @return The ID of this ReliefService
     */
    public int getId() { return this.id; }

    /**
     * Sets the ID of this ReliefService.
     * 
     * @param id The new ID for this ReliefService.
     */
    public void setId(int id) { this.id = id; }
}
