package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class MedicalSkillTest {

    private MedicalSkill skill;

    @Before
    public void setUp() {
        skill = new MedicalSkill(ProficiencyLevel.INTERMEDIATE,
            MedicalSkill.Certification.NURSING, LocalDate.of(2027, 1, 1));
    }

    @Test
    public void testGetCertification() {
        assertEquals(MedicalSkill.Certification.NURSING, skill.getCertification());
    }

    @Test
    public void testGetProficiencyLevel() {
        assertEquals(ProficiencyLevel.INTERMEDIATE, skill.getProficiencyLevel());
    }

    @Test
    public void testGetCertificationExpiration() {
        assertEquals(LocalDate.of(2027, 1, 1), skill.getCertificationExpiration());
    }

    @Test
    public void testSetCertification() {
        skill.setCertification(MedicalSkill.Certification.DOCTOR);
        assertEquals(MedicalSkill.Certification.DOCTOR, skill.getCertification());
    }

    @Test
    public void testGetSkillCategory() {
        assertEquals(SkillCategory.MEDICAL, skill.getSkillCategory());
    }
}