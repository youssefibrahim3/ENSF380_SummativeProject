package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class DisasterVictimTest {

    private DisasterVictim victim;
    private static final LocalDate ENTRY = LocalDate.of(2025, 1, 1);

    @Before
    public void setUp() {
        victim = new DisasterVictim("John", ENTRY);
    }

    @Test
    public void testConstructorSetsFirstName() {
        assertEquals("John", victim.getFirstName());
    }

    @Test
    public void testConstructorSetsEntryDate() {
        assertEquals(ENTRY, victim.getEntryDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullEntryDateThrows() {
        new DisasterVictim("John", null);
    }

    @Test
    public void testSetFirstName() {
        victim.setFirstName("Jane");
        assertEquals("Jane", victim.getFirstName());
    }

    @Test
    public void testSetLastName() {
        victim.setLastName("Smith");
        assertEquals("Smith", victim.getLastName());
    }

    @Test
    public void testSetDateOfBirthValid() {
        LocalDate dob = LocalDate.of(2000, 6, 15);
        victim.setDateOfBirth(dob);
        assertEquals(dob, victim.getDateOfBirth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfBirthFutureThrows() {
        victim.setDateOfBirth(LocalDate.now().plusDays(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfBirthNullThrows() {
        victim.setDateOfBirth(null);
    }

    @Test
    public void testSetDateOfBirthClearsApproxAge() {
        victim.setApproxAge(25);
        victim.setDateOfBirth(LocalDate.of(2000, 1, 1));
        assertEquals(0, victim.getApproxAge());
    }

    @Test
    public void testSetApproxAgeValid() {
        victim.setApproxAge(30);
        assertEquals(30, victim.getApproxAge());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetApproxAgeZeroThrows() {
        victim.setApproxAge(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetApproxAgeNegativeThrows() {
        victim.setApproxAge(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetApproxAgeWhenDOBExistsThrows() {
        victim.setDateOfBirth(LocalDate.of(2000, 1, 1));
        victim.setApproxAge(25);
    }

    @Test
    public void testSetGenderMan() {
        victim.setGender("man");
        assertEquals("Man", victim.getGender());
    }

    @Test
    public void testSetGenderWoman() {
        victim.setGender("woman");
        assertEquals("Woman", victim.getGender());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetGenderInvalidThrows() {
        victim.setGender("alien");
    }

    @Test
    public void testSetDeletedTrue() {
        victim.setDeleted(true);
        assertTrue(victim.isDeleted());
    }

    @Test
    public void testIsDeletedFalseByDefault() {
        assertFalse(victim.isDeleted());
    }

    @Test
    public void testSetRequirement() {
        victim.setRequirement("dietary restrictions", "halal");
        assertEquals("halal", victim.getRequirements().get("dietary restrictions"));
    }

    @Test
    public void testSetRequirementOverwritesSameCategory() {
        victim.setRequirement("dietary restrictions", "halal");
        victim.setRequirement("dietary restrictions", "vegetarian");
        assertEquals("vegetarian", victim.getRequirements().get("dietary restrictions"));
    }

    @Test
    public void testRegisterSkillAddsToList() {
        TradeSkill skill = new TradeSkill(ProficiencyLevel.BEGINNER, TradeSkill.SkillType.CARPENTRY);
        victim.registerSkill(skill);
        assertEquals(1, victim.getSkills().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterDuplicateTradeSkillThrows() {
        TradeSkill s1 = new TradeSkill(ProficiencyLevel.BEGINNER, TradeSkill.SkillType.CARPENTRY);
        TradeSkill s2 = new TradeSkill(ProficiencyLevel.ADVANCED, TradeSkill.SkillType.CARPENTRY);
        victim.registerSkill(s1);
        victim.registerSkill(s2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterDuplicateMedicalSkillThrows() {
        MedicalSkill s1 = new MedicalSkill(ProficiencyLevel.BEGINNER,
            MedicalSkill.Certification.NURSING, LocalDate.now().plusYears(1));
        MedicalSkill s2 = new MedicalSkill(ProficiencyLevel.ADVANCED,
            MedicalSkill.Certification.NURSING, LocalDate.now().plusYears(2));
        victim.registerSkill(s1);
        victim.registerSkill(s2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterDuplicateLanguageSkillThrows() {
        LanguageSkill s1 = new LanguageSkill(ProficiencyLevel.BEGINNER, "French",
            new LanguageSkill.Capabilities[]{LanguageSkill.Capabilities.SPEAK_LISTEN});
        LanguageSkill s2 = new LanguageSkill(ProficiencyLevel.ADVANCED, "french",
            new LanguageSkill.Capabilities[]{LanguageSkill.Capabilities.READ_WRITE});
        victim.registerSkill(s1);
        victim.registerSkill(s2);
    }

    @Test
    public void testRegisterDifferentSkillCategoriesAllowed() {
        victim.registerSkill(new TradeSkill(ProficiencyLevel.BEGINNER, TradeSkill.SkillType.CARPENTRY));
        victim.registerSkill(new MedicalSkill(ProficiencyLevel.BEGINNER,
            MedicalSkill.Certification.FIRST_AID, LocalDate.now().plusYears(1)));
        assertEquals(2, victim.getSkills().size());
    }

    @Test
    public void testRemoveSkillReducesListSize() {
        TradeSkill skill = new TradeSkill(ProficiencyLevel.BEGINNER, TradeSkill.SkillType.PLUMBING);
        victim.registerSkill(skill);
        victim.removeSkill(skill);
        assertEquals(0, victim.getSkills().size());
    }

    @Test
    public void testAddMedicalRecordIncreasesCount() {
        Location loc = new Location("Test", "123 St");
        MedicalRecord record = new MedicalRecord(loc, "treatment", LocalDate.now());
        victim.addMedicalRecord(record);
        assertEquals(1, victim.getMedicalRecords().length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullMedicalRecordThrows() {
        victim.addMedicalRecord(null);
    }

    @Test
    public void testAddFamilyConnectionIncreasesCount() {
        DisasterVictim v2 = new DisasterVictim("Jane", ENTRY);
        FamilyRelation rel = new FamilyRelation(victim, "sibling", v2);
        victim.addFamilyConnection(rel);
        assertEquals(1, victim.getFamilyConnections().length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullFamilyConnectionThrows() {
        victim.addFamilyConnection(null);
    }

    @Test
    public void testAddPersonalBelonging() {
        Supply s = new Supply("blanket", 0, false, null, 1, 0, "test");
        victim.addPersonalBelonging(s);
        assertEquals(1, victim.getPersonalBelongings().length);
    }

    @Test
    public void testRemovePersonalBelonging() {
        Supply s = new Supply("blanket", 0, false, null, 1, 0, "test");
        victim.addPersonalBelonging(s);
        victim.removePersonalBelonging(s);
        assertEquals(0, victim.getPersonalBelongings().length);
    }

    @Test
    public void testSetComments() {
        victim.setComments("test comment");
        assertEquals("test comment", victim.getComments());
    }

    @Test
    public void testSetId() {
        victim.setId(42);
        assertEquals(42, victim.getId());
    }

    @Test
    public void testSetLocationId() {
        victim.setLocationId(3);
        assertEquals(3, victim.getLocationId());
    }
}