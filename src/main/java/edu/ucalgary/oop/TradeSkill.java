package edu.ucalgary.oop;


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
