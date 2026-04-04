/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;
import java.time.LocalDate;

public class MedicalSkill extends Skill {
    public enum Certification {
        FIRST_AID,
        COUNSELING,
        NURSING,
        DOCTOR
    };
    private Certification certification;
    private LocalDate certificationExpiration;

    public MedicalSkill(ProficiencyLevel level, Certification certification, LocalDate certificationExpiration)
    {
        super(SkillCategory.MEDICAL, level);
        this.certification = certification;
        this.certificationExpiration = certificationExpiration;
    }

    public Certification getCertification()
    {
        return this.certification;
    }
    public void setCertification(Certification certification)
    {
        this.certification = certification;
    }

    public LocalDate getCertificationExpiration()
    {
        return this.certificationExpiration;
    }
    public void setCertificationExpiration(LocalDate certificationExpiration)
    {
        this.certificationExpiration = certificationExpiration;
    }


}
