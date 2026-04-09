package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class TradeSkillTest {

    private TradeSkill skill;

    @Before
    public void setUp() {
        skill = new TradeSkill(ProficiencyLevel.BEGINNER, TradeSkill.SkillType.CARPENTRY);
    }

    @Test
    public void testGetSkillType() {
        assertEquals(TradeSkill.SkillType.CARPENTRY, skill.getSkillType());
    }

    @Test
    public void testGetProficiencyLevel() {
        assertEquals(ProficiencyLevel.BEGINNER, skill.getProficiencyLevel());
    }

    @Test
    public void testGetSkillCategory() {
        assertEquals(SkillCategory.TRADE, skill.getSkillCategory());
    }

    @Test
    public void testSetSkillType() {
        skill.setSkillType(TradeSkill.SkillType.PLUMBING);
        assertEquals(TradeSkill.SkillType.PLUMBING, skill.getSkillType());
    }
}