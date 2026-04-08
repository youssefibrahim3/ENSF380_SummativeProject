/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

import java.time.LocalDate;

public class MedicalRecord {
    private Location location;
    private String treatmentDetails;
    private LocalDate dateOfTreatment; 

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
     * @return Location
     */
    // Getter and setter for location
    public Location getLocation() {
        return location;
    }

    /** 
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /** 
     * @return String
     */
    // Getter for treatmentDetails
    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    /** 
     * @param treatmentDetails
     * @throws IllegalArgumentException
     */
    // Setter for treatmentDetails
    public void setTreatmentDetails(String treatmentDetails) throws IllegalArgumentException {
        this.treatmentDetails = treatmentDetails;
    }

    /** 
     * @return LocalDate
     */
    public LocalDate getDateOfTreatment() {
        return dateOfTreatment;
    }

    /** 
     * @param dateOfTreatment
     * @throws IllegalArgumentException
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
