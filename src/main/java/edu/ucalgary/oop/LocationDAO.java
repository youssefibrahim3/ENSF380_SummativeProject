package edu.ucalgary.oop;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO implements GenericDAO<Location, Integer> {
    private final Connection connection;

    /**
     * Constructs a new LocationDAO object with the specified connection
     * 
     * @param connection The Connection to the database
     */
    public LocationDAO(Connection connection)
    {
        this.connection = connection;
    }

    /**
     * Builds a Location object from data obtained from the database
     * 
     * @param rs The ResultSet to take the parameters from
     * @return A Location created with the provided data
     * @throws SQLException If there was an error with getting the ResultSet
     */
    private Location build(ResultSet rs) throws SQLException 
    {
        Location s = new Location(
            rs.getString("name"),
            rs.getString("address")
        );
        s.setId(rs.getInt("id"));
        return s;
    }

    /** 
     * Gets all of the locations inside of the database.
     * 
     * @return A list of all the found locations
     */
    @Override
    public List<Location> getAll()
    {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM Location";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                locations.add(build(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error getting locations: " + e.getMessage());
        }
        return locations;
    }

    /** 
     * Gets a location by ID from the database.
     * 
     * @param locationId The ID of the location to look for
     * @return The Location if one was found, null otherwise
     */
    @Override
    public Location getById(Integer locationId)
    {
        String sql = "SELECT * FROM Location WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,locationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return build(rs);
            }
            rs.close();
            ps.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Error finding location: " + e.getMessage());
            return null;
        }
    }

    /** 
     * Inserts a new location into the database.
     * 
     * @param location The location to insert to the database
     * @return True if the operation was successful, false otherwise
     */
    @Override 
    public boolean insert(Location location)
    {
        String sql = "INSERT INTO Location (name, address) VALUES (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, location.getName());
            ps.setString(2, location.getAddress());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                location.setId(rs.getInt(1));
            }
            rs.close();
            ps.close();
            ActionLogger.getInstance().log("INSERTED", "location " + location.getName()); 
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to insert location: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Updates a location on the database.
     * 
     * @param location The Location to update with. The updated location corresponds to location.getId()
     * @return True if the operation was successful, false otherwise
     */
    @Override
    public boolean update(Location location)
    {
        String sql = "UPDATE Location SET name = ?, address = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, location.getName());
            ps.setString(2, location.getAddress());
            ps.setInt(3, location.getId());

            ps.executeUpdate();
            ps.close();
            ActionLogger.getInstance().log("UPDATED", "location " + location.getId()); 
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating supply: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Deletes a location on the database.
     * 
     * @param locationId The ID of the location to delete
     * @return True if the operation was successful, false otherwise
     */
    @Override
    public boolean delete(Integer locationId)
    {
        String sql = "DELETE FROM Location WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,locationId);
            ps.executeUpdate();
            ActionLogger.getInstance().log("DELETED", "location " + locationId);
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting supply: " + e.getMessage());
            return false;
        }
    }

}
