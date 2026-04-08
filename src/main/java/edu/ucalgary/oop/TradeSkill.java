package edu.ucalgary.oop;

public class TradeSkill extends Skill {
    
    public enum SkillType {
        CARPENTRY,
        PLUMBING,
        ELECTRICITY
    };
    private SkillType skillType;

    public TradeSkill(ProficiencyLevel level, SkillType skillType)
    {
        super(SkillCategory.TRADE, level);
        this.skillType = skillType;
    }

    public SkillType getSkillType()
    {
        return this.skillType;
    }
    public void setSkillType(SkillType skillType)
    {
        this.skillType = skillType;
    }

    
}
