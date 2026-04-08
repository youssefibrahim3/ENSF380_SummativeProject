package edu.ucalgary.oop;

import java.time.LocalDate;

/**
 * Class representing a single medical record
 * 
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-04-06
 */

public class MedicalRecord {
    private Location location;
    private String treatmentDetails;
    private LocalDate dateOfTreatment; 

    /**
     * Constructs a new MedicalRecord object with the provided parameters.
     * 
     * @param location The location this medicalrecord was at
     * @param treatmentDetails Details of treatment
     * @param dateOfTreatment A LocalDate representing the date this treatment occured
     * @throws IllegalArgumentException if dateOfTreatement is null or is after the current day
     */
    public MedicalRecord(Location location, String treatmentDetails, LocalDate dateOfTreatment) throws IllegalArgumentException {
        setLocation(location);
        this.treatmentDetails = treatmentDetails;
        
        if (dateOfTreatment == null) {
            throw new IllegalArgumentException("Date of treatment cannot be null");
        }
        if (dateOfTreatment.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of treatment cannot be in the future");
        }
        this.dateOfTreatment = dateOfTreatment;
    }

    /** 
     * Gets the location associated with this MedicalRecord.
     * 
     * @return The location associated with this MedicalRecord
     */
    public Location getLocation() {
        return location;
    }

    /** 
     * Sets the location associated with this MedicalRecord.
     * 
     * @param location The new location to be associated with this MedicalRecord
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /** 
     * Gets the details of treatment from this MedicalRecord.
     * 
     * @return A String containing the treatment details
     */
    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    /** 
     * Sets the details of the treatment for this MedicalRecord.
     * 
     * @param treatmentDetails A String containing the new treatment details
     * @throws IllegalArgumentException
     */
    public void setTreatmentDetails(String treatmentDetails) throws IllegalArgumentException {
        this.treatmentDetails = treatmentDetails;
    }

    /** 
     * Gets the date of treatment associated with this MedicalRecord.
     * 
     * @return A LocalDate representing the date of treatment
     */
    public LocalDate getDateOfTreatment() {
        return dateOfTreatment;
    }

    /** 
     * Sets the date of treatment associated with this MedicalRecord.
     * 
     * @param dateOfTreatment A LocalDate representing the new date of treatment
     * @throws IllegalArgumentException if dateOfTreatment is null or is after the current day
     */
    public void setDateOfTreatment(LocalDate dateOfTreatment) throws IllegalArgumentException {
        if (dateOfTreatment == null) {
            throw new IllegalArgumentException("Date of treatment cannot be null");
        }
        if (dateOfTreatment.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of treatment cannot be in the future");
        }
        this.dateOfTreatment = dateOfTreatment;
    }
}
