/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

public class LanguageSkill extends Skill {
    public enum Capabilities
    {
        READ_WRITE,
        SPEAK_LISTEN
    };
    private Capabilities[] capabilities;
    private String language;

    public LanguageSkill(ProficiencyLevel level, String language, Capabilities[] capabilities)
    {
        super(SkillCategory.LANGUAGE, level);
        this.language = language;
        this.capabilities = capabilities;
    }
    
    public String getLanguage()
    {
        return this.language;
    }
    public void setLanguage(String language)
    {
        this.language = language;
    }

    public Capabilities[] getCapabilities()
    {
        return this.capabilities;
    }
    public void setCapabilities(Capabilities[] capabilities)
    {
        this.capabilities = capabilities;
    }
}
