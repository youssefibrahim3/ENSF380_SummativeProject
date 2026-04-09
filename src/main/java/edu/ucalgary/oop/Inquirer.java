package edu.ucalgary.oop;

/**
 * Class representing a single inquirer in the database
 * 
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-03-30
 */
public class Inquirer {
    private final String FIRST_NAME;
    private final String LAST_NAME;
    private final String INFO;
    private final String SERVICES_PHONE;
    private int id;

    /**
     * Constructs a new Inquirer object with the specified attributes.
     * 
     * @param firstName The first name of the inquirer
     * @param lastName The last name of the inquirer
     * @param phone The phone number of the inquirer
     * @param info Information regarding the inquirer
     */
    public Inquirer(String firstName, String lastName, String phone, String info) {
        this.FIRST_NAME = firstName;
        this.LAST_NAME = lastName;
        this.SERVICES_PHONE = phone;
        this.INFO = info;
    }

    /**
     * Gets the id of the inquirer.
     * 
     * @return The id associated with the inquirer
     */
    public int getId() { return this.id; }
    /**
     * Gets the id of the inquirer.
     * 
     * @return The id associated with the inquirer
     */
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return this.FIRST_NAME; }
    public String getLastName() { return this.LAST_NAME; }
    public String getServicesPhoneNum() { return this.SERVICES_PHONE; }
    public String getInfo() { return this.INFO; }
}
