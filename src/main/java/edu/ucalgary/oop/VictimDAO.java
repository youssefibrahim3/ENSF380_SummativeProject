package edu.ucalgary.oop;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VictimDAO implements GenericDAO<DisasterVictim, Integer> {

    private final Connection connection;

    public VictimDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<DisasterVictim> getAll() {
        List<DisasterVictim> victims = new ArrayList<>();
        String sql = "SELECT p.id, p.first_name, p.last_name, p.comments, " +
                     "dv.gender, dv.date_of_birth, dv.approximate_age, dv.entry_date " +
                     "FROM Person p JOIN DisasterVictim dv ON p.id = dv.person_id " +
                     "WHERE dv.is_soft_deleted = FALSE";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                victims.add(buildVictim(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting victims: " + e.getMessage());
        }
        return victims;
    }

    @Override
    public DisasterVictim getById(Integer personId) {
        String sql = "SELECT p.id, p.first_name, p.last_name, p.comments, " +
                     "dv.gender, dv.date_of_birth, dv.approximate_age, dv.entry_date " +
                     "FROM Person p JOIN DisasterVictim dv ON p.id = dv.person_id " +
                     "WHERE p.id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return buildVictim(rs);
        } catch (SQLException e) {
            System.out.println("Error finding victim: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean insert(DisasterVictim victim) {
        String personSQL = "INSERT INTO Person (first_name, last_name, comments) VALUES (?, ?, ?) RETURNING id";
        String victimSQL = "INSERT INTO DisasterVictim (person_id, gender, entry_date, location_id, date_of_birth, approximate_age) " +
                           "VALUES (?, ?, CURRENT_DATE, ?, ?, ?)";
        try {
            // Step 1: insert into Person, get the new ID
            PreparedStatement ps1 = connection.prepareStatement(personSQL);
            ps1.setString(1, victim.getFirstName());
            ps1.setString(2, victim.getLastName());
            ps1.setString(3, victim.getComments());
            ResultSet rs = ps1.executeQuery();
            rs.next();
            int newId = rs.getInt("id");
            victim.setId(newId);

            // Step 2: insert into DisasterVictim
            PreparedStatement ps2 = connection.prepareStatement(victimSQL);
            ps2.setInt(1, newId);
            ps2.setString(2, victim.getGender());
            ps2.setInt(3, victim.getLocationId());

            // Feature 5: only one of dob or approxAge, never both
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

    @Override
    public boolean update(DisasterVictim victim) {
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


    //Feature 4
    @Override
    public boolean delete(Integer personId) {
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

    public boolean softDelete(Integer personId) {
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

    //Feature 5
    public boolean updateApproximateAge(int personId, int newAge) {
        String checkSQL = "SELECT date_of_birth FROM DisasterVictim WHERE person_id = ?";
        String updateSQL = "UPDATE DisasterVictim SET approximate_age = ? WHERE person_id = ?";
        try {
            // Don't allow if they already have a real DOB
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

    //Feature 5
    public boolean replaceAgeWithDOB(int personId, LocalDate dob) {
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

    private DisasterVictim buildVictim(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String comments = rs.getString("comments");
        String gender = rs.getString("gender");
        LocalDate entryDate = rs.getDate("entry_date").toLocalDate();

        Date dobSQL = rs.getDate("date_of_birth");
        LocalDate dob = (dobSQL != null) ? dobSQL.toLocalDate() : null;
        int approxAge = rs.getInt("approximate_age");

        // Use the right constructor based on what data we have
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
        if (gender != null) v.setGender(gender);
        return v;
    }
}