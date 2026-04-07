package edu.ucalgary.oop;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplyDAO implements GenericDAO<Supply, Integer> {
    private final Connection connection;

    private Supply build(ResultSet rs) throws SQLException 
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

        // Use the right constructor based on what data we have
        Supply s;
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

    @Override
    public List<Supply> getAll()
    {
        List<Supply> supplies = new ArrayList<>();
        String sql = "stuff";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                supplies.add(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error getting supplies: " + e.getMessage());
        }
    }

    @Override
    public Supply getById(Integer supplyId)
    {
        String sql = "stuff";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.SetInt(1,supplyId);
        } catch (SQLException e) {
            System.out.println("Error finding supply: " + e.getMessage());
        }
    }

    @Override 
    public boolean insert(Supply supply)
    {

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
            ps.executeUpdate();

            ActionLogger.getInstance().log("UPDATED", "supply " + supply.getType()); //should this be ID? SHould I add a supplyID var
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
