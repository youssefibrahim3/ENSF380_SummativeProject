package edu.ucalgary.oop;

import java.sql.*;
import java.time.LocalDate;

public class VictimDAO {
    
    public void addVictim(String firstName, String lastName, 
                        Integer approxAge, LocalDate dob) {
        String personSQL = "INSERT INTO Person (first_name, last_name) VALUES (?, ?) RETURNING id";
        String victimSQL = """
            INSERT INTO DisasterVictim 
                (person_id, date_of_birth, approximate_age, entry_date, location_id)
            VALUES (?, ?, ?, CURRENT_DATE, 1)
            """;

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);
            
            int personId;
            try (PreparedStatement ps1 = conn.prepareStatement(personSQL)) {
                ps1.setString(1, firstName);
                ps1.setString(2, lastName);
                ResultSet rs = ps1.executeQuery();
                rs.next();
                personId = rs.getInt("id");
            }

            try (PreparedStatement ps2 = conn.prepareStatement(victimSQL)) {
                ps2.setInt(1, personId);
                if (dob != null) {
                    ps2.setDate(2, java.sql.Date.valueOf(dob));
                    ps2.setNull(3, java.sql.Types.INTEGER);
                } else {
                    ps2.setNull(2, java.sql.Types.DATE);
                    ps2.setInt(3, approxAge);
                }
                ps2.executeUpdate();
            }

            conn.commit();
            ActionLogger.log("ADDED", "disaster victim " + personId + 
                            " | Name: " + firstName + " " + lastName);

        } catch (SQLException e) {
            System.out.println("Failed to add victim: " + e.getMessage());
        }
    }


}
