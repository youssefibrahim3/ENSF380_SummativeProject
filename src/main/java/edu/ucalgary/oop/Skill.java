package edu.ucalgary.oop;

// Note to self: ACCESS MODIFIERS
abstract class Skill {
    private SkillCategory category;
    private ProficiencyLevel level;

    /**
     * 
     * @param category
     * @param level
     */
    public Skill(SkillCategory category, ProficiencyLevel level)
    {
        this.category = category;
        this.level = level;
    }

    /** 
     * @return SkillCategory
     */
    public SkillCategory getSkillCategory()
    {
        return this.category;
    }

    /** 
     * @param category
     */
    public void setSkillCategory(SkillCategory category)
    {
        this.category = category;
    }

    /** 
     * @return ProficiencyLevel
     */
    public ProficiencyLevel getProficiencyLevel()
    {
        return this.level;
    }

    /** 
     * @param level
     */
    public void setProficiencyLevel(ProficiencyLevel level)
    {
        this.level = level;
    }

}
