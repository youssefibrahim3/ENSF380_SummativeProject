package edu.ucalgary.oop;


/**
 * Represents an individual trade skill belonging to a victim.
 * Inherits from the Skill class.
 * 
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-04-04
 */

public class TradeSkill extends Skill {
    
    public enum SkillType {
        CARPENTRY,
        PLUMBING,
        ELECTRICITY
    };
    private SkillType skillType;

    /**
     * Constructs a new TradeSkill object with the specified parameters
     * 
     * @param level The proficiency level the person has in the skill
     * @param skillType The type of TradeSkill
     */
    public TradeSkill(ProficiencyLevel level, SkillType skillType)
    {
        super(SkillCategory.TRADE, level);
        this.skillType = skillType;
    }

    /** 
     * Gets the skill type from the TradeSkill
     * 
     * @return The type of TradeSkill this is
     */
    public SkillType getSkillType()
    {
        return this.skillType;
    }
    /** 
     * Sets the skillType in the trade skill
     * 
     * @param skillType The SkillType to set it to
     */
    public void setSkillType(SkillType skillType)
    {
        this.skillType = skillType;
    }

    
}
