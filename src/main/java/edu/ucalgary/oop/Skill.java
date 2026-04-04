/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

// Note to self: ACCESS MODIFIERS
abstract class Skill {
    private SkillCategory category;
    private ProficiencyLevel level;

    public Skill(SkillCategory category, ProficiencyLevel level)
    {
        this.category = category;
        this.level = level;
    }

    public SkillCategory getSkillCategory()
    {
        return this.category;
    }
    public void setSkillCategory(SkillCategory category)
    {
        this.category = category;
    }

    public ProficiencyLevel getProficiencyLevel()
    {
        return this.level;
    }
    public void setProficiencyLevel(ProficiencyLevel level)
    {
        this.level = level;
    }

}
