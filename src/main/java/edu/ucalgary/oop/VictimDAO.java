package edu.ucalgary.oop;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VictimDAO {

    // ── ADD VICTIM ───────────────────────────────────────────────────────────

    public void addVictim(String firstName, String lastName, String gender,
                          int locationId, Integer approxAge, LocalDate dob) {
        try {
            Connection conn = DatabaseManager.getConnection();

            // Step 1: insert into Person, get back the new ID
            String personSQL = "INSERT INTO Person (first_name, last_name) VALUES (?, ?) RETURNING id";
            PreparedStatement ps1 = conn.prepareStatement(personSQL);
            ps1.setString(1, firstName);
            ps1.setString(2, lastName);
            ResultSet rs = ps1.executeQuery();
            rs.next();
            int newId = rs.getInt("id");

            // Step 2: insert into DisasterVictim using that ID
            String victimSQL = "INSERT INTO DisasterVictim (person_id, gender, entry_date, location_id, date_of_birth, approximate_age) " +
                               "VALUES (?, ?, CURRENT_DATE, ?, ?, ?)";
            PreparedStatement ps2 = conn.prepareStatement(victimSQL);
            ps2.setInt(1, newId);
            ps2.setString(2, gender);
            ps2.setInt(3, locationId);

            // Only one of dob or approxAge is set, never both (Feature 5)
            if (dob != null) {
                ps2.setDate(4, Date.valueOf(dob));
                ps2.setNull(5, Types.INTEGER);
            } else {
                ps2.setNull(4, Types.DATE);
                ps2.setInt(5, approxAge);
            }

            ps2.executeUpdate();
            ActionLogger.log("ADDED", "disaster victim " + newId + " | Name: " + firstName + " " + lastName);

        } catch (SQLException e) {
            System.out.println("Error adding victim: " + e.getMessage());
        }
    }

    public List<DisasterVictim> getAllVictims() {
        List<DisasterVictim> victims = new ArrayList<>();

        try {
            Connection conn = DatabaseManager.getConnection();

            String sql = "SELECT p.id, p.first_name, p.last_name, p.comments, " +
                         "dv.gender, dv.date_of_birth, dv.approximate_age, dv.entry_date, dv.location_id " +
                         "FROM Person p " +
                         "JOIN DisasterVictim dv ON p.id = dv.person_id " +
                         "WHERE dv.is_soft_deleted = FALSE";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Pull columns out
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String comments = rs.getString("comments");
                String gender = rs.getString("gender");
                Date entryDateSQL = rs.getDate("entry_date");
                LocalDate entryDate = entryDateSQL.toLocalDate();

                Date dobSQL = rs.getDate("date_of_birth");
                LocalDate dob = (dobSQL != null) ? dobSQL.toLocalDate() : null;
                int approxAge = rs.getInt("approximate_age"); // 0 if null

                // Build the object using your existing constructors
                DisasterVictim v;
                if (dob != null) {
                    v = new DisasterVictim(firstName, entryDate, dob);
                } else if (approxAge > 0) {
                    v = new DisasterVictim(firstName, entryDate, approxAge);
                } else {
                    v = new DisasterVictim(firstName, entryDate);
                }

                // Set the rest
                v.setId(id);  // you'll need to add this field to DisasterVictim
                v.setLastName(lastName);
                v.setComments(comments);
                v.setGender(gender);

                victims.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Error getting victims: " + e.getMessage());
        }

        return victims;
    }

    public DisasterVictim getVictimById(int personId) {
        try {
            Connection conn = DatabaseManager.getConnection();

            String sql = "SELECT p.id, p.first_name, p.last_name, p.comments, " +
                         "dv.gender, dv.date_of_birth, dv.approximate_age, dv.entry_date " +
                         "FROM Person p " +
                         "JOIN DisasterVictim dv ON p.id = dv.person_id " +
                         "WHERE p.id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
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

                v.setId(personId);
                v.setLastName(lastName);
                v.setComments(comments);
                v.setGender(gender);
                return v;
            }

        } catch (SQLException e) {
            System.out.println("Error finding victim: " + e.getMessage());
        }

        return null;
    }

    public void updateVictim(int personId, String firstName, String lastName, String gender, String comments) {
        try {
            Connection conn = DatabaseManager.getConnection();

            String personSQL = "UPDATE Person SET first_name = ?, last_name = ?, comments = ? WHERE id = ?";
            PreparedStatement ps1 = conn.prepareStatement(personSQL);
            ps1.setString(1, firstName);
            ps1.setString(2, lastName);
            ps1.setString(3, comments);
            ps1.setInt(4, personId);
            ps1.executeUpdate();

            String victimSQL = "UPDATE DisasterVictim SET gender = ? WHERE person_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(victimSQL);
            ps2.setString(1, gender);
            ps2.setInt(2, personId);
            ps2.executeUpdate();

            ActionLogger.log("UPDATED", "disaster victim " + personId + " | Name: " + firstName + " " + lastName);

        } catch (SQLException e) {
            System.out.println("Error updating victim: " + e.getMessage());
        }
    }

    //Feature 4
    public void softDeleteVictim(int personId) {
        try {
            Connection conn = DatabaseManager.getConnection();

            String sql = "UPDATE DisasterVictim SET is_soft_deleted = TRUE WHERE person_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, personId);
            ps.executeUpdate();

            ActionLogger.log("SOFT DELETED", "disaster victim " + personId);

        } catch (SQLException e) {
            System.out.println("Error soft deleting victim: " + e.getMessage());
        }
    }

    // Deleting from Person cascades to DisasterVictim, MedicalRecord, etc. automatically
    public void hardDeleteVictim(int personId) {
        try {
            Connection conn = DatabaseManager.getConnection();

            String sql = "DELETE FROM Person WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, personId);
            ps.executeUpdate();

            ActionLogger.log("DELETED", "disaster victim " + personId);

        } catch (SQLException e) {
            System.out.println("Error deleting victim: " + e.getMessage());
        }
    }

    //Feature 5
    public void updateApproximateAge(int personId, int newAge) {
        try {
            Connection conn = DatabaseManager.getConnection();

            // Check first — do they have a real DOB?
            String checkSQL = "SELECT date_of_birth FROM DisasterVictim WHERE person_id = ?";
            PreparedStatement check = conn.prepareStatement(checkSQL);
            check.setInt(1, personId);
            ResultSet rs = check.executeQuery();

            if (rs.next() && rs.getDate("date_of_birth") != null) {
                System.out.println("Cannot set approximate age — this victim already has a date of birth.");
                return;
            }

            String sql = "UPDATE DisasterVictim SET approximate_age = ? WHERE person_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newAge);
            ps.setInt(2, personId);
            ps.executeUpdate();

            ActionLogger.log("UPDATED", "disaster victim " + personId + " | approximate age -> " + newAge);

        } catch (SQLException e) {
            System.out.println("Error updating approximate age: " + e.getMessage());
        }
    }

    //Feature 5
    public void replaceAgeWithDOB(int personId, LocalDate dob) {
        try {
            Connection conn = DatabaseManager.getConnection();

            String sql = "UPDATE DisasterVictim SET date_of_birth = ?, approximate_age = NULL WHERE person_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(dob));
            ps.setInt(2, personId);
            ps.executeUpdate();

            ActionLogger.log("UPDATED", "disaster victim " + personId + " | approximate age replaced with DOB: " + dob);

        } catch (SQLException e) {
            System.out.println("Error replacing age with DOB: " + e.getMessage());
        }
    }
    
}