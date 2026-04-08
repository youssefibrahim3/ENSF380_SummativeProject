package edu.ucalgary.oop;

public class LanguageSkill extends Skill {
    public enum Capabilities
    {
        READ_WRITE,
        SPEAK_LISTEN
    };
    private Capabilities[] capabilities;
    private String language;

    /**
     * Constructs a new LanguageSkill object with the specified parameters
     * 
     * @param level The ProficiencyLevel in the language
     * @param language The language that this skill represents
     * @param capabilities The person's capabilities with the language
     */
    public LanguageSkill(ProficiencyLevel level, String language, Capabilities[] capabilities)
    {
        super(SkillCategory.LANGUAGE, level);
        this.language = language;
        this.capabilities = capabilities;
    }
    
    /** 
     * Gets the language from the LanguageSkill
     * 
     * @return A String containing the language name
     */
    public String getLanguage()
    {
        return this.language;
    }

    /** 
     * Sets the language in the LanguageSkill
     * 
     * @param language A String containing the language name
     */
    public void setLanguage(String language)
    {
        this.language = language;
    }

    /** 
     * Gets the capabilities the victim has with this language
     * 
     * @return A list of the capabilities
     */
    public Capabilities[] getCapabilities()
    {
        return this.capabilities;
    }

    /** 
     * Sets the capabilities the victim has with this language
     * 
     * @param capabilities A list of the capabilities
     */
    public void setCapabilities(Capabilities[] capabilities)
    {
        this.capabilities = capabilities;
    }
}
