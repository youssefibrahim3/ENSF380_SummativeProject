package edu.ucalgary.oop;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplyDAO implements GenericDAO<Supply, Integer> {
    private final Connection connection;

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

    @Override
    public Supply getById(Integer supplyId)
    {
        String sql = "SELECT * FROM Supply WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,supplyId);
            ResultSet rs = ps.executeQuery();
            ps.close();
            return build(rs);
        } catch (SQLException e) {
            System.out.println("Error finding supply: " + e.getMessage());
            return null;
        }
    }

    @Override 
    public boolean insert(Supply supply)
    {
        String sql = "INSERT INTO Supply (supply_type, location_id, victim_id, expiry_date, allocation_date, description VALUES" +
                    "(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, supply.getType());
            ps.setInt(2, supply.getLocationId());
            ps.setInt(3, supply.getVictimId());
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
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to insert supply: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Supply supply)
    {
        String sql = "UPDATE Supply SET supply_type = ?, location_id = ?, victim_id = ?, expiry_date = ?, description = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, supply.getType());
            ps.setInt(2, supply.getLocationId());
            ps.setInt(3, supply.getVictimId());
            if (supply.getExpirationDate() != null)
            {
                ps.setDate(4, Date.valueOf(supply.getExpirationDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, supply.getDescription());
            ps.setInt(6, supply.getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                supply.setId(rs.getInt(1));
            }
            ps.close();
            ActionLogger.getInstance().log("UPDATED", "supply " + supply.getId()); 
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating supply: " + e.getMessage());
            return false;
        }
    }

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

}
