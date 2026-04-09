package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class LanguageSkillTest {

    private LanguageSkill skill;

    @Before
    public void setUp() {
        skill = new LanguageSkill(ProficiencyLevel.ADVANCED, "French",
            new LanguageSkill.Capabilities[]{LanguageSkill.Capabilities.SPEAK_LISTEN});
    }

    @Test
    public void testGetLanguage() {
        assertEquals("French", skill.getLanguage());
    }

    @Test
    public void testGetProficiencyLevel() {
        assertEquals(ProficiencyLevel.ADVANCED, skill.getProficiencyLevel());
    }

    @Test
    public void testGetCapabilities() {
        assertEquals(1, skill.getCapabilities().length);
    }

    @Test
    public void testGetSkillCategory() {
        assertEquals(SkillCategory.LANGUAGE, skill.getSkillCategory());
    }

    @Test
    public void testSetLanguage() {
        skill.setLanguage("Spanish");
        assertEquals("Spanish", skill.getLanguage());
    }
}