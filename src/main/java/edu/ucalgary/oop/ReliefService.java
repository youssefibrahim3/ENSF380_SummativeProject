/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;
import java.time.LocalDate;
// REMOVED: import java.time.format.DateTimeFormatter; // No longer needed

// Treating this as more of an individual "inquiry" 
public class ReliefService {
    private Inquirer inquirer;
    private DisasterVictim missingPerson;
    private LocalDate dateOfInquiry; 
    private String infoProvided;
    private Location lastKnownLocation;
    private int id;

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
     * @return Inquirer
     */
    // Getter and setter for inquirer
    public Inquirer getInquirer() {
        return inquirer;
    }

    /** 
     * @param inquirer
     */
    public void setInquirer(Inquirer inquirer) {
        this.inquirer = inquirer;
    }

    /** 
     * @return DisasterVictim
     */
    // Getter and setter for missingPerson
    public DisasterVictim getMissingPerson() {
        return missingPerson;
    }

    /** 
     * @param missingPerson
     */
    public void setMissingPerson(DisasterVictim missingPerson) {
        this.missingPerson = missingPerson;
    }

    /** 
     * @return LocalDate
     */
    public LocalDate getDateOfInquiry() {
        return dateOfInquiry;
    }

    /** 
     * @param dateOfInquiry
     * @throws IllegalArgumentException
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
     * @return String
     */
    // Getter and setter for infoProvided
    public String getInfoProvided() {
        return infoProvided;
    }

    /** 
     * @param infoProvided
     */
    public void setInfoProvided(String infoProvided) {
        this.infoProvided = infoProvided;
    }

    /** 
     * @return Location
     */
    // Getter and setter for lastKnownLocation
    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    /** 
     * @param lastKnownLocation
     */
    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }
    
    /** 
     * @return String
     */
    public String getLogDetails() {
       return "Inquirer: " + inquirer.getFirstName() + 
           ", Missing Person: " + missingPerson.getFirstName() + 
           ", Date of Inquiry: " + dateOfInquiry + 
           ", Info Provided: " + infoProvided + 
           ", Last Known Location: " + lastKnownLocation.getName();
    }

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
}
