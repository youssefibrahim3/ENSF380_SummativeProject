package edu.ucalgary.oop;
import java.time.LocalDate;

/**
 * Represents an individual medical skill belonging to a victim.
 * Inherits from the Skill class.
 * 
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-04-04
 */

public class MedicalSkill extends Skill {
    public enum Certification {
        FIRST_AID,
        COUNSELING,
        NURSING,
        DOCTOR
    };
    private Certification certification;
    private LocalDate certificationExpiration;

    /**
     * Constructs a new MedicalSkill object with the specified proficiency level and skill type
     * 
     * @param level The proficiency level the person has in the skill
     * @param skillType The type of MedicalSkill
     */
    public MedicalSkill(ProficiencyLevel level, Certification certification, LocalDate certificationExpiration)
    {
        super(SkillCategory.MEDICAL, level);
        this.certification = certification;
        this.certificationExpiration = certificationExpiration;
    }

    /** 
     * Gets the certification type from the MedicalSkill
     * 
     * @return The certification type for this MedicalSkill
     */
    public Certification getCertification()
    {
        return this.certification;
    }

    /** 
     * Sets the certification type in the MedicalSkill
     * 
     * @param certification The Certification to set it to
     */
    public void setCertification(Certification certification)
    {
        this.certification = certification;
    }

    /** 
     * Gets the certification expiration date from the TradeSkill
     * 
     * @return A LocalDate representing the expiration date of the certification
     */
    public LocalDate getCertificationExpiration()
    {
        return this.certificationExpiration;
    }

    /** 
     * Sets the expiration date for the certification in the medical skill
     * 
     * @param certificationExpiration A LocalDate representing the expiration date of the certification
     */
    public void setCertificationExpiration(LocalDate certificationExpiration)
    {
        this.certificationExpiration = certificationExpiration;
    }


}
