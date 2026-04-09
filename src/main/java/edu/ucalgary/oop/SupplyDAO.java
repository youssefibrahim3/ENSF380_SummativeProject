package edu.ucalgary.oop;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * PostgreSQL implementation of GenericDAO for Supply entities.
 * Handles all database operations for supplies including CRUD operations.
 * 
 * @author Youssef Ibrahim
 * @version 2.0
 * @since 2026-04-03
 */

public class SupplyDAO implements GenericDAO<Supply, Integer> {
    private final Connection connection;

    /**
     * Constructs a new SupplyDAO object with the specified connection
     * 
     * @param connection The Connection to the database
     */
    public SupplyDAO(Connection connection)
    {
        this.connection = connection;
    }

    /**
     * Builds a Supply object from data obtained from the database
     * 
     * @param rs The ResultSet to take the parameters from
     * @return A Supply created with the provided data
     * @throws SQLException If there was an error with getting the ResultSet
     */
    private Supply build(ResultSet rs) throws SQLException 
    {
        Supply s = new Supply(
            rs.getString("supply_type"),
            0,
            rs.getDate("expiry_date") != null,
            rs.getDate("expiry_date") != null ? rs.getDate("expiry_date").toLocalDate() : null,
            rs.getInt("location_id"),
            rs.getInt("victim_id"),
            rs.getString("description")
        );
        
        s.setId(rs.getInt("id"));
        return s;
    }

    /** 
     * Gets all of the supplies inside of the database.
     * 
     * @return A list of all the found supplies
     */
    @Override
    public List<Supply> getAll()
    {
        List<Supply> supplies = new ArrayList<>();
        String sql = "SELECT * FROM Supply";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                supplies.add(build(rs));
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error getting supplies: " + e.getMessage());
        }
        return supplies;
    }

    /** 
     * Gets a supply by ID from the database.
     * 
     * @param supplyId The ID of the supply to look for
     * @return The Supply if one was found, null otherwise
     */
    @Override
    public Supply getById(Integer supplyId)
    {
        String sql = "SELECT * FROM Supply WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,supplyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return build(rs);
            }
            ps.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Error finding supply: " + e.getMessage());
            return null;
        }
    }

    /** 
     * Inserts a new supply into the database.
     * 
     * @param location The supply to insert to the database
     * @return True if the operation was successful, false otherwise
     */
    @Override 
    public boolean insert(Supply supply)
    {
        String sql = "INSERT INTO Supply (supply_type, location_id, victim_id, expiry_date, allocation_date, description) VALUES" +
                    "(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, supply.getType());
            if (supply.getLocationId() != 0)
            {
                ps.setInt(2, supply.getLocationId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            if (supply.getVictimId() != 0)
            {
                ps.setInt(3, supply.getVictimId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            if (supply.getExpirationDate() != null)
            {
                ps.setDate(4, Date.valueOf(supply.getExpirationDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setDate(5, Date.valueOf(LocalDate.now()));
            ps.setString(6, supply.getDescription());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                supply.setId(rs.getInt(1));
            }
            ps.close();
            ActionLogger.getInstance().log("INSERTED", "supply " + supply.getId()); 
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to insert supply: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Updates a supply on the database.
     * 
     * @param location The Supply to update with. The updated supply corresponds to supply.getId()
     * @return True if the operation was successful, false otherwise
     */
    @Override
    public boolean update(Supply supply)
    {
        String sql = "UPDATE Supply SET supply_type = ?, location_id = ?, victim_id = ?, expiry_date = ?, description = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, supply.getType());
            if (supply.getLocationId() != 0)
            {
                ps.setInt(2, supply.getLocationId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            if (supply.getVictimId() != 0)
            {
                ps.setInt(3, supply.getVictimId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            if (supply.getExpirationDate() != null)
            {
                ps.setDate(4, Date.valueOf(supply.getExpirationDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, supply.getDescription());
            ps.setInt(6, supply.getId());
            ps.executeUpdate();
            ps.close();
            ActionLogger.getInstance().log("UPDATED", "supply " + supply.getId()); 
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating supply: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Deletes a supply on the database.
     * 
     * @param supplyId The ID of the supply to delete
     * @return True if the operation was successful, false otherwise
     */
    @Override
    public boolean delete(Integer supplyId)
    {
        String sql = "DELETE FROM Supply WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,supplyId);
            ps.executeUpdate();
            ActionLogger.getInstance().log("DELETED", "supply " + supplyId);
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting supply: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Gets all of the non-expired supplies that are in the database.
     * 
     * @return A list of all non-expired supplies found
     */
    public List<Supply> getNonExpiredSupplies() 
    {
    List<Supply> supplies = new ArrayList<>();
    String sql = "SELECT * FROM Supply WHERE expiry_date IS NULL OR expiry_date > CURRENT_DATE";

    try {
         PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            supplies.add(build(rs));
        }
         ps.close();
    } catch (SQLException e) {
        System.out.println("Error getting supplies: " + e.getMessage());
    }
    return supplies;
    }
}