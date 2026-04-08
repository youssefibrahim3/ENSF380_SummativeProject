package edu.ucalgary.oop;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InquiryDAO implements GenericDAO<ReliefService, Integer> {
    private final Connection connection;

    /**
     * Constructs a new InquiryDAO object with the specified connection
     * 
     * @param connection The Connection to the database
     */
    public InquiryDAO(Connection connection)
    {
        this.connection = connection;
    }

    /** 
     * Builds a ReliefService object from data obtained from the database.
     * 
     * @param rs The ResultSet to take the parameters from
     * @return A ReliefService created with the provided data
     * @throws SQLException If there was an error with getting the ResultSet
     */
    private ReliefService build(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int inquirerId = rs.getInt("inquirer_id");
        int subjectPersonId = rs.getInt("subject_person_id");
        String details = rs.getString("details");
        LocalDate inquiryDate = rs.getTimestamp("inquiry_date").toLocalDateTime().toLocalDate();

        String inquirerSQL = "SELECT * FROM Person WHERE id = ?";
        PreparedStatement ps1 = connection.prepareStatement(inquirerSQL);
        ps1.setInt(1, inquirerId);
        ResultSet rs1 = ps1.executeQuery();
        
        Inquirer inquirer = null;
        if (rs1.next()) {
            inquirer = new Inquirer(
                rs1.getString("first_name"),
                rs1.getString("last_name"),
                null,   // phone not in DB schema
                rs1.getString("comments")
            );
            inquirer.setId(inquirerId);
        }

        VictimDAO victimDAO = new VictimDAO(connection);
        DisasterVictim missingPerson = victimDAO.getById(subjectPersonId);

        ReliefService inquiry = new ReliefService(inquirer, missingPerson, inquiryDate, details, null);
        inquiry.setId(id);
        ps1.close();
        rs.close();
        return inquiry;
    }

    /** 
     * Gets all of the inquiries inside of the database.
     * 
     * @return A list of all of the found inquiries
     */
    @Override
    public List<ReliefService> getAll()
    {
        List<ReliefService> inquiries = new ArrayList<>();
        String sql = "SELECT * FROM Inquiry";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                inquiries.add(build(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error getting inquiries: " + e.getMessage());
        }
        return inquiries;
    }

    /** 
     * Gets an inquiry by ID from the database.
     * 
     * @param inquiryId The ID of the inquiry to look for
     * @return The Inquiry/ReliefService if one was found, null otherwise
     */
    @Override
    public ReliefService getById(Integer inquiryId)
    {
        String sql = "SELECT * FROM Inquiry WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,inquiryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return build(rs);
            }
            ps.close();
            rs.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Error finding inquiry: " + e.getMessage());
            return null;
        }
    }

    /** 
     * Inserts a new inquiry into the database.
     * 
     * @param inquiry The inquiry (ReliefService) to insert to the database
     * @return True if the operation was successful, false otherwise
     */
    @Override 
    public boolean insert(ReliefService inquiry)
    {
        String sql = "INSERT INTO Inquiry (inquirer_id, subject_person_id, details) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, inquiry.getInquirer().getId());
            ps.setInt(2, inquiry.getMissingPerson().getId());
            ps.setString(3, inquiry.getLogDetails());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                inquiry.setId(rs.getInt(1));
            }
    
            ps.close();
            rs.close();
            ActionLogger.getInstance().log("INSERTED", "inquiry " + inquiry.getId()); 
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to insert inquiry: " + e.getMessage());
            return false;
        }
    }

    /** 
     * Updates an inquiry on the database.
     * 
     * @param inquiry The ReliefService to update with. The updated inquiry corresponds to inquiry.getId()
     * @return True if the operation was successful, false otherwise
     */
    @Override
    public boolean update(ReliefService inquiry)
    {
        String sql = "UPDATE Inquiry SET inquirer_id = ?, subject_person_id = ?, inquiry_date = ?, details = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, inquiry.getInquirer().getId());
            ps.setInt(2, inquiry.getMissingPerson().getId());
            ps.setDate(3, Date.valueOf(inquiry.getDateOfInquiry()));
            ps.setString(4, inquiry.getLogDetails());
            ps.setInt(5, inquiry.getId());
            ps.executeUpdate();
            ps.close();
            ActionLogger.getInstance().log("UPDATED", "inquiry " + inquiry.getId()); 
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating inquiry: " + e.getMessage());
            return false;
        }
    }

    /** 
     * @param inquiryId
     * @return True if the operation was successful, false otherwise
     */
    @Override
    public boolean delete(Integer inquiryId)
    {
        String sql = "DELETE FROM Inquiry WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,inquiryId);
            ps.executeUpdate();
            ActionLogger.getInstance().log("DELETED", "inquiry " + inquiryId);
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting inquiry: " + e.getMessage());
            return false;
        }
    }

}
