/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class representing a single victim/person
 * 
 * @author Youssef Ibrahim
 * @version 3.0
 * @since 2026-03-30
 */

public class DisasterVictim {
    private String firstName;
    private String lastName;

    private LocalDate dateOfBirth; 
    private int approxAge;

    private FamilyRelation[] familyConnections; 
    private MedicalRecord[] medicalRecords; 
    private Supply[] personalBelongings;
    private final LocalDate ENTRY_DATE; 
    private String gender;
    private String comments;

    private int id;
    private int location_id;

    private HashMap<String, String> requirements;
    private boolean isDeleted;
    private List<Skill> skills;
    
    public DisasterVictim(String firstName, LocalDate ENTRY_DATE) throws IllegalArgumentException {
        if (ENTRY_DATE == null) {
            throw new IllegalArgumentException("Entry date cannot be null");
        }
        this.firstName = firstName;
        this.ENTRY_DATE = ENTRY_DATE;
        this.familyConnections = new FamilyRelation[0];
        this.medicalRecords = new MedicalRecord[0];
        this.personalBelongings = new Supply[0];

        this.skills = new ArrayList<>();
        this.requirements = new HashMap<>();
    }

    public DisasterVictim(String firstName, LocalDate ENTRY_DATE, LocalDate dateOfBirth) throws IllegalArgumentException {
        this(firstName, ENTRY_DATE);
        setDateOfBirth(dateOfBirth);
    }

    public DisasterVictim(String firstName, LocalDate ENTRY_DATE, int approxAge) throws IllegalArgumentException {
        this(firstName, ENTRY_DATE);
        setApproxAge(approxAge);
    }

    /** 
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /** 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** 
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /** 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** 
     * @return LocalDate
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /** 
     * @param location_id
     */
    public void setLocationId(int location_id)
    {
        this.location_id = location_id;
    }
    /** 
     * @return int
     */
    public int getLocationId()
    {
        return this.location_id;
    }

    /** 
     * @param dateOfBirth
     * @throws IllegalArgumentException
     */
    public void setDateOfBirth(LocalDate dateOfBirth) throws IllegalArgumentException {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }

        // Check if the date is in the future
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }

        this.dateOfBirth = dateOfBirth;
        this.approxAge = 0;
    }

    /** 
     * @return FamilyRelation[]
     */
    public FamilyRelation[] getFamilyConnections() {
        return familyConnections;
    }

    /** 
     * @return MedicalRecord[]
     */
    public MedicalRecord[] getMedicalRecords() {
        return medicalRecords;
    }

    /** 
     * @return Supply[]
     */
    public Supply[] getPersonalBelongings() {
        return this.personalBelongings;
    }

    /** 
     * @param connections
     */
    public void setFamilyConnections(FamilyRelation[] connections) {
        this.familyConnections = connections != null ? connections.clone() : new FamilyRelation[0];
    }

    /** 
     * @param records
     */
    public void setMedicalRecords(MedicalRecord[] records) {
        this.medicalRecords = records != null ? records.clone() : new MedicalRecord[0];
    }

    /** 
     * @param belongings
     */
    public void setPersonalBelongings(Supply[] belongings) {
        this.personalBelongings = belongings != null ? belongings.clone() : new Supply[0];
    }

    /** 
     * @param supply
     */
    public void addPersonalBelonging(Supply supply) {
        if (supply == null) {
            throw new IllegalArgumentException("Supply cannot be null");
        }

        
        if (this.personalBelongings == null) {
            Supply tmpSupply[] = { supply };
            this.setPersonalBelongings(tmpSupply);
            return;
        }

        int newLength = this.personalBelongings.length + 1;
        Supply tmpPersonalBelongings[] = new Supply[newLength];

        int i;
        for (i=0; i < personalBelongings.length; i++) {
            tmpPersonalBelongings[i] = this.personalBelongings[i];
        }

        tmpPersonalBelongings[i] = supply;

        this.personalBelongings = tmpPersonalBelongings;
    }

    /** 
     * @param unwantedSupply
     * @throws IllegalArgumentException
     */
    public void removePersonalBelonging(Supply unwantedSupply) throws IllegalArgumentException {
        if (unwantedSupply == null) {
            throw new IllegalArgumentException("Supply to remove cannot be null");
        }
        
        int index = -1;
        for (int i = 0; i < personalBelongings.length; i++) {
            if (personalBelongings[i].equals(unwantedSupply)) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            throw new IllegalArgumentException("Supply not found in personal belongings");
        }
        
        Supply[] updatedBelongings = new Supply[personalBelongings.length - 1];
        int newIndex = 0;
        for (int i = 0; i < personalBelongings.length; i++) {
            if (i != index) {
                updatedBelongings[newIndex] = personalBelongings[i];
                newIndex++;
            }
        }
        
        this.personalBelongings = updatedBelongings;
    }

    /** 
     * @param exRelation
     * @throws IllegalArgumentException
     */
    public void removeFamilyConnection(FamilyRelation exRelation) throws IllegalArgumentException {
        if (exRelation == null) {
            throw new IllegalArgumentException("Family relation to remove cannot be null");
        }
        
        int index = -1;
        for (int i = 0; i < familyConnections.length; i++) {
            if (familyConnections[i].equals(exRelation)) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            throw new IllegalArgumentException("Family relation not found");
        }
        
        FamilyRelation[] updatedConnections = new FamilyRelation[familyConnections.length - 1];
        int newIndex = 0;
        for (int i = 0; i < familyConnections.length; i++) {
            if (i != index) {
                updatedConnections[newIndex] = familyConnections[i];
                newIndex++;
            }
        }
        
        this.familyConnections = updatedConnections;
    }

    /** 
     * @param record
     */
    public void addFamilyConnection(FamilyRelation record) {
        if (record == null) {
            throw new IllegalArgumentException("Family relation cannot be null");
        }
        
        FamilyRelation[] newConnections = new FamilyRelation[familyConnections.length + 1];
        System.arraycopy(familyConnections, 0, newConnections, 0, familyConnections.length);
        newConnections[familyConnections.length] = record;
        this.familyConnections = newConnections;
    }

    /** 
     * @param record
     */
    public void addMedicalRecord(MedicalRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("Medical record cannot be null");
        }
        
        MedicalRecord[] newRecords = new MedicalRecord[medicalRecords.length + 1];
        System.arraycopy(medicalRecords, 0, newRecords, 0, medicalRecords.length);
        newRecords[medicalRecords.length] = record;
        this.medicalRecords = newRecords;
    }

    /** 
     * @return LocalDate
     */
    public LocalDate getEntryDate() {
        return ENTRY_DATE;
    }

    /** 
     * @return String
     */
    public String getComments() {
        return comments;
    }

    /** 
     * @param comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /** 
     * @return String
     */
    public String getGender() {
        return gender;
    }

    /** 
     * @param gender
     * @throws IllegalArgumentException
     */
    public void setGender(String gender) throws IllegalArgumentException {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be null or empty");
        }
        
        String normalizedGender = gender.trim();
        String lowerGender = normalizedGender.toLowerCase();
        
        // If gender is already set to "Please specify", allow any new value
        if (this.gender != null && this.gender.equalsIgnoreCase("please specify")) {
            this.gender = normalizedGender;
            return;
        }
        
        String[] adultOptions = {"man", "woman", "non-binary person"};
        String[] childOptions = {"boy", "girl"};
        
        if (lowerGender.equals("please specify")) {
            this.gender = normalizedGender;
            return;
        }
        if (lowerGender.equals("non-binary person")) {
            this.gender = "non-binary person";
            return;
        }
            
        boolean isValidOption = false;
        String properCaseOption = null;
        
        for (String option : adultOptions) {
            if (lowerGender.equals(option)) {
                isValidOption = true;
                properCaseOption = option.substring(0, 1).toUpperCase() + option.substring(1);
                break;
            }
        }
        
        if (!isValidOption) {
            for (String option : childOptions) {
                if (lowerGender.equals(option)) {
                    isValidOption = true;
                    properCaseOption = option.substring(0, 1).toUpperCase() + option.substring(1);
                    break;
                }
            }
        }
        
        if (!isValidOption) {
            throw new IllegalArgumentException(
                "Invalid gender. Acceptable values are: Man, Woman, Boy, Girl, or 'Please specify'"
            );
        }
        
        if (this.dateOfBirth != null) {
            int age = LocalDate.now().getYear() - this.dateOfBirth.getYear();
            boolean isAdult = age >= 18;
            
            if (isAdult) {
                for (String childOption : childOptions) {
                    if (lowerGender.equals(childOption)) {
                        throw new IllegalArgumentException(
                            "Cannot set gender to '" + properCaseOption + "' for an adult (age " + age + ")"
                        );
                    }
                }
            } else {
                for (String adultOption : adultOptions) {
                    if (lowerGender.equals(adultOption)) {
                        throw new IllegalArgumentException(
                            "Cannot set gender to '" + properCaseOption + "' for a child (age " + age + ")"
                        );
                    }
                }
            }
        }
        
        this.gender = properCaseOption;
    }


    /** 
     * @return boolean
     */
    //Feature 4

    public boolean isDeleted()
    {
        return this.isDeleted;
    }
    /** 
     * @param isDeleted
     */
    public void setDeleted(boolean isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    /** 
     * @return int
     */
    public int getId()
    {
        return this.id;
    }
    /** 
     * @param id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /** 
     * @return int
     */
    //Feature 5
    public int getApproxAge() {
        return approxAge;
    }
    /** 
     * @param approxAge
     */
    public void setApproxAge(int approxAge)
    {
        if (approxAge <= 0)
        {
            throw new IllegalArgumentException("Approximate age must be a nonzero positive number.");
        }

        // If date of birth exists, cant approximate age
        if (this.dateOfBirth != null)
        {
            throw new IllegalArgumentException("Cannot set approximate age when date of birth exists");
        }

        this.approxAge = approxAge;
        this.dateOfBirth = null;
    }

    /** 
     * @param type
     * @param value
     */
    //Feature 7

    public void setRequirement(String type, String value)
    {
        this.requirements.put(type, value);
    }
    /** 
     * @return HashMap<String, String>
     */
    public HashMap<String, String> getRequirements()
    {
        return this.requirements;
    }
    
    /** 
     * @param newSkill
     */
    //Feature 8
    
    public void registerSkill(Skill newSkill) {
        for (Skill s : this.skills) {
            if (s instanceof MedicalSkill && newSkill instanceof MedicalSkill) {
                if (((MedicalSkill)s).getCertification() == ((MedicalSkill)newSkill).getCertification()) {
                    throw new IllegalArgumentException("Victim already has this medical skill registered.");
                }
            }
            if (s instanceof LanguageSkill && newSkill instanceof LanguageSkill) {
                if (((LanguageSkill)s).getLanguage().equalsIgnoreCase(((LanguageSkill)newSkill).getLanguage())) {
                    throw new IllegalArgumentException("Victim already has this language registered.");
                }
            }
            if (s instanceof TradeSkill && newSkill instanceof TradeSkill) {
                if (((TradeSkill)s).getSkillType() == ((TradeSkill)newSkill).getSkillType()) {
                    throw new IllegalArgumentException("Victim already has this trade skill registered.");
                }
            }
        }
        this.skills.add(newSkill);
    }

    /** 
     * @param skill
     */
    public void removeSkill(Skill skill)
    {
        this.skills.remove(skill);
    }
    /** 
     * @return List<Skill>
     */
    public List<Skill> getSkills()
    {
        return this.skills;
    }
    /** 
     * @param skills
     */
    public void setSkills(List<Skill> skills)
    {
        this.skills = skills;
    }

}
