package edu.ucalgary.oop;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * PostgreSQL implementation of GenericDAO for Victim entities.
 * Handles all database operations for victims including CRUD operations,
 * and also managing skills, medical records, and anything else
 * associated with a victim.
 * 
 * @author Youssef Ibrahim
 * @version 3.0
 * @since 2026-04-03
 */

public class VictimDAO implements GenericDAO<DisasterVictim, Integer> {

    private final Connection connection;

    /**
     * Constructs a new VictimDAO with the provided connection.
     * 
     * @param connection The database connection for the DAO
     */
    public VictimDAO(Connection connection) 
    {
        this.connection = connection;
    }


    /** 
     * Loads a victim's skills.
     * 
     * @param victimId The ID of the victim
     * @return A list of all of the victim's skills
     * @throws SQLException
     */
    private List<Skill> loadSkills(int victimId) throws SQLException {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT s.skill_name, s.category, vs.proficiency_level, " +
                    "vs.details, vs.language_capabilities, vs.certification_expiry " +
                    "FROM VictimSkill vs JOIN Skill s ON vs.skill_id = s.id " +
                    "WHERE vs.victim_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, victimId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String category = rs.getString("category");
            ProficiencyLevel level = ProficiencyLevel.valueOf(rs.getString("proficiency_level").toUpperCase());

            if (category.equals("medical")) {
                MedicalSkill.Certification cert = MedicalSkill.Certification.valueOf(
                    rs.getString("skill_name").toUpperCase().replace("-", "_"));
                Date expiry = rs.getDate("certification_expiry");
                skills.add(new MedicalSkill(level, cert, 
                    expiry != null ? expiry.toLocalDate() : null));

            } else if (category.equals("language")) {
                String caps = rs.getString("language_capabilities");
                List<LanguageSkill.Capabilities> capList = new ArrayList<>();
                if (caps != null) {
                    if (caps.contains("read/write")) capList.add(LanguageSkill.Capabilities.READ_WRITE);
                    if (caps.contains("speak/listen")) capList.add(LanguageSkill.Capabilities.SPEAK_LISTEN);
                }
                skills.add(new LanguageSkill(level, rs.getString("skill_name"),
                    capList.toArray(new LanguageSkill.Capabilities[0])));

            } else {
                TradeSkill.SkillType type = TradeSkill.SkillType.valueOf(
                    rs.getString("skill_name").toUpperCase());
                skills.add(new TradeSkill(level, type));
            }
        }
        return skills;
    }

    /** 
     * Builds a DisasterVictimVictim object from a ResultSet
     * 
     * @param rs the ResultSet containing the victim's information
     * @return A DisasterVictim made from the ResultSet
     * @throws SQLException
     */
    private DisasterVictim build(ResultSet rs) throws SQLException 
    {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String comments = rs.getString("comments");
        String gender = rs.getString("gender");
        LocalDate entryDate = rs.getDate("entry_date").toLocalDate();

        Date dobSQL = rs.getDate("date_of_birth");
        LocalDate dob = (dobSQL != null) ? dobSQL.toLocalDate() : null;
        int approxAge = rs.getInt("approximate_age");

        DisasterVictim v;
        if (dob != null) {
            v = new DisasterVictim(firstName, entryDate, dob);
        } else if (approxAge > 0) {
            v = new DisasterVictim(firstName, entryDate, approxAge);
        } else {
            v = new DisasterVictim(firstName, entryDate);
        }

        v.setId(id);
        v.setLastName(lastName);
        v.setComments(comments);
        v.setSkills(loadSkills(id));

        if (gender != null) v.setGender(gender);
        loadCulturalRequirements(v);
        return v;
    }

    /** 
     * Gets a list of all victims.
     * 
     * @return A list of all DisasterVictims
     */
    @Override
    public List<DisasterVictim> getAll() 
    {
        List<DisasterVictim> victims = new ArrayList<>();
        String sql = "SELECT p.id, p.first_name, p.last_name, p.comments, " +
                     "dv.gender, dv.date_of_birth, dv.approximate_age, dv.entry_date " +
                     "FROM Person p JOIN DisasterVictim dv ON p.id = dv.person_id " +
                     "WHERE dv.is_soft_deleted = FALSE";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                victims.add(build(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting victims: " + e.getMessage());
        }
        return victims;
    }

    /** 
     * Gets a victim by ID.
     * 
     * @param personId The ID of the victim
     * @return The DisasterVictim at this ID, if found.
     */
    @Override
    public DisasterVictim getById(Integer personId) 
    {
        String sql = "SELECT p.id, p.first_name, p.last_name, p.comments, " +
                     "dv.gender, dv.date_of_birth, dv.approximate_age, dv.entry_date " +
                     "FROM Person p JOIN DisasterVictim dv ON p.id = dv.person_id " +
                     "WHERE p.id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return build(rs);
        } catch (SQLException e) {
            System.out.println("Error finding victim: " + e.getMessage());
        }
        return null;
    }

    /** 
     * Inserts a new victim into the database.
     * 
     * @param victim The victim to insert.
     * @return True if operation successful, false otherwise
     */
    @Override
    public boolean insert(DisasterVictim victim) 
    {
        String personSQL = "INSERT INTO Person (first_name, last_name, comments) VALUES (?, ?, ?) RETURNING id";
        String victimSQL = "INSERT INTO DisasterVictim (person_id, gender, entry_date, location_id, date_of_birth, approximate_age) " +
                           "VALUES (?, ?, CURRENT_DATE, ?, ?, ?)";
        try {
            PreparedStatement ps1 = connection.prepareStatement(personSQL);
            ps1.setString(1, victim.getFirstName());
            ps1.setString(2, victim.getLastName());
            ps1.setString(3, victim.getComments());
            ResultSet rs = ps1.executeQuery();
            rs.next();
            int newId = rs.getInt("id");
            victim.setId(newId);

            PreparedStatement ps2 = connection.prepareStatement(victimSQL);
            ps2.setInt(1, newId);
            ps2.setString(2, victim.getGender());
            ps2.setInt(3, victim.getLocationId());

            //Feature 5
            if (victim.getDateOfBirth() != null) {
                ps2.setDate(4, Date.valueOf(victim.getDateOfBirth()));
                ps2.setNull(5, Types.INTEGER);
            } else {
                ps2.setNull(4, Types.DATE);
                ps2.setInt(5, victim.getApproxAge());
            }

            ps2.executeUpdate();
            ActionLogger.getInstance().log("ADDED", "disaster victim " + newId +
                " | Name: " + victim.getFirstName() + " " + victim.getLastName());
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding victim: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Updates a victim in the database.
     * 
     * @param victim The victim to update
     * @return True if operation successful, false otherwise
     */
    @Override
    public boolean update(DisasterVictim victim) 
    {
        String personSQL = "UPDATE Person SET first_name = ?, last_name = ?, comments = ? WHERE id = ?";
        String victimSQL = "UPDATE DisasterVictim SET gender = ? WHERE person_id = ?";
        try {
            PreparedStatement ps1 = connection.prepareStatement(personSQL);
            ps1.setString(1, victim.getFirstName());
            ps1.setString(2, victim.getLastName());
            ps1.setString(3, victim.getComments());
            ps1.setInt(4, victim.getId());
            ps1.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement(victimSQL);
            ps2.setString(1, victim.getGender());
            ps2.setInt(2, victim.getId());
            ps2.executeUpdate();

            ActionLogger.getInstance().log("UPDATED", "disaster victim " + victim.getId() +
                " | Name: " + victim.getFirstName() + " " + victim.getLastName());
            return true;

        } catch (SQLException e) {
            System.out.println("Error updating victim: " + e.getMessage());
            return false;
        }
    }


    /** 
     * Hard-deletes a victim.
     * 
     * @param personId The ID of the victim to delete.
     * @return True if operation successful, false otherwise
     */
    //Feature 4
    @Override
    public boolean delete(Integer personId) 
    {
        String sql = "DELETE FROM Person WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, personId);
            ps.executeUpdate();
            ActionLogger.getInstance().log("DELETED", "disaster victim " + personId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting victim: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Soft-deletes a victim. (so they are not removed from the database)
     * 
     * @param personId
     * @return True if operation successful, false otherwise
     */
    public boolean softDelete(Integer personId) 
    {
        String sql = "UPDATE DisasterVictim SET is_soft_deleted = TRUE WHERE person_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, personId);
            ps.executeUpdate();
            ActionLogger.getInstance().log("SOFT DELETED", "disaster victim " + personId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error soft deleting victim: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Updates the approximate age of a victim,
     * if the victim does not already has a date of birth.
     * 
     * @param personId The ID of the victim
     * @param newAge The new approximate age
     * @return True if operation successful, false otherwise
     */
    //Feature 5
    public boolean updateApproximateAge(int personId, int newAge) 
    {
        String checkSQL = "SELECT date_of_birth FROM DisasterVictim WHERE person_id = ?";
        String updateSQL = "UPDATE DisasterVictim SET approximate_age = ? WHERE person_id = ?";
        try {
            PreparedStatement check = connection.prepareStatement(checkSQL);
            check.setInt(1, personId);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getDate("date_of_birth") != null) {
                System.out.println("Cannot set approximate age — victim already has a date of birth.");
                return false;
            }

            PreparedStatement ps = connection.prepareStatement(updateSQL);
            ps.setInt(1, newAge);
            ps.setInt(2, personId);
            ps.executeUpdate();
            ActionLogger.getInstance().log("UPDATED", "disaster victim " + personId + " | approximate age -> " + newAge);
            return true;

        } catch (SQLException e) {
            System.out.println("Error updating approximate age: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Updates the date of birth of a victim.
     * Sets the approximate age to NULL.
     * 
     * @param personId The victim's ID
     * @param dob The date of birth for the victim
     * @return True if operation successful, false otherwise
     */
    //Feature 5
    public boolean replaceAgeWithDOB(int personId, LocalDate dob) 
    {
        String sql = "UPDATE DisasterVictim SET date_of_birth = ?, approximate_age = NULL WHERE person_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(dob));
            ps.setInt(2, personId);
            ps.executeUpdate();
            ActionLogger.getInstance().log("UPDATED", "disaster victim " + personId + " | approximate age replaced with DOB: " + dob);
            return true;
        } catch (SQLException e) {
            System.out.println("Error replacing age with DOB: " + e.getMessage());
            return false;
        }
    }




    /** 
     * Loads the cultural requirements for a victim.
     * 
     * @param victim The victim to load from
     */
    //Feature 7

    public void loadCulturalRequirements(DisasterVictim victim) {
        String sql = "SELECT requirement_category, requirement_option " +
                    "FROM CulturalRequirement WHERE victim_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, victim.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                victim.setRequirement(
                    rs.getString("requirement_category"),
                    rs.getString("requirement_option")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error loading cultural requirements: " + e.getMessage());
        }
    }

    /** 
     * Inserts a cultural requirement into the database.
     * If one already exists, then it updates it.
     * 
     * @param victimId The ID of the victim
     * @param category The requirement category
     * @param option The requirement option
     * @return True if operation successful, false otherwise
     */
    public boolean insertCulturalRequirement(int victimId, String category, String option) {
        String sql = "INSERT INTO CulturalRequirement (victim_id, requirement_category, requirement_option) " +
                    "VALUES (?, ?, ?) " +
                    "ON CONFLICT (victim_id, requirement_category) DO UPDATE SET requirement_option = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, victimId);
            ps.setString(2, category);
            ps.setString(3, option);
            ps.setString(4, option);
            ps.executeUpdate();
            ActionLogger.getInstance().log("UPDATED", "cultural requirement for victim " + victimId +
                " | " + category + ": " + option);
            return true;
        } catch (SQLException e) {
            System.out.println("Error saving cultural requirement: " + e.getMessage());
            return false;
        }
    }

    //Feature 8

    /** 
     * Inserts a new skill to a victim in the database.
     * 
     * @param victimId The ID of the victim
     * @param skill The Skill to insert
     * @return True if operation successful, false otherwise
     */

    public boolean insertSkill(int victimId, Skill skill) {
        String skillName = getSkillName(skill);
        String category = skill.getSkillCategory().name().toLowerCase();
        String proficiency = skill.getProficiencyLevel().name().toLowerCase();

        String skillSQL = "INSERT INTO Skill (skill_name, category) VALUES (?, ?) " +
                        "ON CONFLICT (skill_name, category) DO NOTHING";
        String getSkillIdSQL = "SELECT id FROM Skill WHERE skill_name = ? AND category = ?";
        String victimSkillSQL = "INSERT INTO VictimSkill (victim_id, skill_id, details, language_capabilities, " +
                                "certification_expiry, proficiency_level) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps1 = connection.prepareStatement(skillSQL);
            ps1.setString(1, skillName);
            ps1.setString(2, category);
            ps1.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement(getSkillIdSQL);
            ps2.setString(1, skillName);
            ps2.setString(2, category);
            ResultSet rs = ps2.executeQuery();
            rs.next();
            int skillId = rs.getInt("id");

            PreparedStatement ps3 = connection.prepareStatement(victimSkillSQL);
            ps3.setInt(1, victimId);
            ps3.setInt(2, skillId);

            if (skill instanceof MedicalSkill) {
                MedicalSkill ms = (MedicalSkill) skill;
                ps3.setString(3, ms.getCertification().name().toLowerCase());
                ps3.setNull(4, Types.VARCHAR);
                if (ms.getCertificationExpiration() != null)
                    ps3.setDate(5, Date.valueOf(ms.getCertificationExpiration()));
                else
                    ps3.setNull(5, Types.DATE);
            } else if (skill instanceof LanguageSkill) {
                LanguageSkill ls = (LanguageSkill) skill;
                ps3.setNull(3, Types.VARCHAR);

                StringBuilder caps = new StringBuilder();
                for (LanguageSkill.Capabilities c : ls.getCapabilities()) {
                    if (caps.length() > 0) caps.append(", ");
                    caps.append(c == LanguageSkill.Capabilities.READ_WRITE ? "read/write" : "speak/listen");
                }
                ps3.setString(4, caps.toString());
                ps3.setNull(5, Types.DATE);
            } else {
                // TradeSkill — no extra fields needed
                ps3.setNull(3, Types.VARCHAR);
                ps3.setNull(4, Types.VARCHAR);
                ps3.setNull(5, Types.DATE);
            }

            ps3.setString(6, proficiency);
            ps3.executeUpdate();

            ActionLogger.getInstance().log("ADDED", "skill " + skillName + 
                " (" + category + ") for victim " + victimId);
            return true;

        } catch (SQLException e) {
            System.out.println("Error inserting skill: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Removes a skill from a victim in the database.
     * 
     * @param victimId The ID of the victim
     * @param skill The Skill
     * @return True if operation successful, false otherwise
     */

    public boolean deleteSkill(int victimId, Skill skill) {
        String skillName = getSkillName(skill);
        String category = skill.getSkillCategory().name().toLowerCase();
        String sql = "DELETE FROM VictimSkill WHERE victim_id = ? AND skill_id = " +
                    "(SELECT id FROM Skill WHERE skill_name = ? AND category = ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, victimId);
            ps.setString(2, skillName);
            ps.setString(3, category);
            ps.executeUpdate();
            ActionLogger.getInstance().log("DELETED", "skill " + skillName + 
                " (" + category + ") from victim " + victimId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting skill: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Search all victims by skill category
     * 
     * @param category The category of skills to search for
     * @return A list of victims that fit the criteria
     */
    public List<DisasterVictim> getVictimsBySkillCategory(String category) {
        List<DisasterVictim> results = new ArrayList<>();
        String sql = "SELECT DISTINCT p.id, p.first_name, p.last_name, p.comments, " +
                    "dv.gender, dv.date_of_birth, dv.approximate_age, dv.entry_date " +
                    "FROM Person p " +
                    "JOIN DisasterVictim dv ON p.id = dv.person_id " +
                    "JOIN VictimSkill vs ON dv.person_id = vs.victim_id " +
                    "JOIN Skill s ON vs.skill_id = s.id " +
                    "WHERE s.category = ? AND dv.is_soft_deleted = FALSE";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, category.toLowerCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                results.add(build(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error searching skills: " + e.getMessage());
        }
        return results;
    }

    /** 
     * Gets the skill name from any Skill type
     * 
     * @param skill The Skill to get the name of
     * @return The name of the skill
     */
    private String getSkillName(Skill skill) {
        if (skill instanceof MedicalSkill) {
            return ((MedicalSkill) skill).getCertification().name().toLowerCase().replace("_", "-");
        } else if (skill instanceof LanguageSkill) {
            return ((LanguageSkill) skill).getLanguage();
        } else {
            return ((TradeSkill) skill).getSkillType().name().toLowerCase();
        }  
    }

    /** 
     * Inserts a new medical record for a victim into the database
     * 
     * @param victimId The ID of the victim
     * @param record The MedicalRecord to insert
     * @return True if operation successful, false otherwise
     */

    public boolean insertMedicalRecord(int victimId, MedicalRecord record) {
        String sql = "INSERT INTO MedicalRecord (victim_id, treatment_details, treatment_date, location_id) " +
                    "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, victimId);
            ps.setString(2, record.getTreatmentDetails());
            ps.setDate(3, Date.valueOf(record.getDateOfTreatment()));
            ps.setInt(4, record.getLocation().getId());
            ps.executeUpdate();
            ActionLogger.getInstance().log("ADDED", "medical record for victim " + victimId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error inserting medical record: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Loads medical records for a victim.
     * 
     * @param victim The DisasterVictim to load from
     * @param locationDAO The locationDAO (to load a LocationID into the medical record)
     */
    public void loadMedicalRecords(DisasterVictim victim, LocationDAO locationDAO) {
        String sql = "SELECT * FROM MedicalRecord WHERE victim_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, victim.getId());
            ResultSet rs = ps.executeQuery();
            List<MedicalRecord> records = new ArrayList<>();
            while (rs.next()) {
                Location loc = locationDAO.getById(rs.getInt("location_id"));
                MedicalRecord record = new MedicalRecord(
                    loc,
                    rs.getString("treatment_details"),
                    rs.getDate("treatment_date").toLocalDate()
                );
                records.add(record);
            }
            victim.setMedicalRecords(records.toArray(new MedicalRecord[0]));
        } catch (SQLException e) {
            System.out.println("Error loading medical records: " + e.getMessage());
        }
    }

    /** 
     * Inserts a new family relationship into the database.
     * 
     * @param personOneId The ID of the first person in the relationship
     * @param personTwoId The ID of the second person in the relationship
     * @param relationshipType The type of relationship
     * @return True if operation successful, false otherwise
     */

    public boolean insertFamilyRelationship(int personOneId, int personTwoId, String relationshipType) {
        String sql = "INSERT INTO FamilyRelationship (person_one_id, person_two_id, relationship_type) " +
                    "VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, personOneId);
            ps.setInt(2, personTwoId);
            ps.setString(3, relationshipType);
            ps.executeUpdate();
            ActionLogger.getInstance().log("ADDED", "family relationship between victim " +
                personOneId + " and " + personTwoId + " | " + relationshipType);
            return true;
        } catch (SQLException e) {
            System.out.println("Error inserting family relationship: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Loads all family relationships for a victim.
     * 
     * @param victim The DisasterVictim to load relationships from
     */
    public void loadFamilyRelationships(DisasterVictim victim) {
        String sql = "SELECT * FROM FamilyRelationship WHERE person_one_id = ? OR person_two_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, victim.getId());
            ps.setInt(2, victim.getId());
            ResultSet rs = ps.executeQuery();
            List<FamilyRelation> relations = new ArrayList<>();
            while (rs.next()) {
                int personOneId = rs.getInt("person_one_id");
                int personTwoId = rs.getInt("person_two_id");
                String type = rs.getString("relationship_type");

                DisasterVictim personOne = getById(personOneId);
                DisasterVictim personTwo = getById(personTwoId);

                if (personOne != null && personTwo != null) {
                    relations.add(new FamilyRelation(personOne, type, personTwo));
                }
            }
            victim.setFamilyConnections(relations.toArray(new FamilyRelation[0]));
        } catch (SQLException e) {
            System.out.println("Error loading family relationships: " + e.getMessage());
        }
    }

    /**
     * Deletes the relationship between two people in the database.
     * 
     * @param personOneId The ID of the first person in the relationship
     * @param personTwoId The ID of the second person in the relationship
     * @return True if operation successful, false otherwise
     */
    public boolean deleteFamilyRelationship(int personOneId, int personTwoId) {
        String sql = "DELETE FROM FamilyRelationship WHERE person_one_id = ? AND person_two_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, personOneId);
            ps.setInt(2, personTwoId);
            ps.executeUpdate();
            ActionLogger.getInstance().log("DELETED", "family relationship between " + personOneId + " and " + personTwoId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting relationship: " + e.getMessage());
            return false;
        }
    }

}

