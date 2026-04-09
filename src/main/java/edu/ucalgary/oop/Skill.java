package edu.ucalgary.oop;

/**
 * Abstract base class representing an individual skill belonging to a victim
 * 
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-04-04
 */

abstract class Skill {
    private SkillCategory category;
    private ProficiencyLevel level;

    /**
     * Constructs a new Skill object with the specified parameters
     * 
     * @param category The category of skill from SkillCategory (MEDICAL, LANGUAGE, TRADE)
     * @param level The proficiency level that the victim has with the skill (BEGINNER, INTERMEDIATE, ADVANCED)
     */
    public Skill(SkillCategory category, ProficiencyLevel level)
    {
        this.category = category;
        this.level = level;
    }

    /** 
     * Gets the category of this skill
     * 
     * @return The SkillCategory corresponding to the Skill object
     */
    public SkillCategory getSkillCategory()
    {
        return this.category;
    }

    /** 
     * Sets the category of this skill
     * 
     * @param category a SkillCategory (MEDICAL, LANGUAGE, TRADE)
     */
    public void setSkillCategory(SkillCategory category)
    {
        this.category = category;
    }

    /** 
     * Gets the proficiency level in this skill
     * 
     * @return The ProficiencyLevel corresponding to this skill
     */
    public ProficiencyLevel getProficiencyLevel()
    {
        return this.level;
    }

    /** 
     * Sets the proficiency level in this skill
     * 
     * @param level a ProficiencyLevel (BEGINNER, INTERMEDIATE, ADVANCED)
     */
    public void setProficiencyLevel(ProficiencyLevel level)
    {
        this.level = level;
    }

}
