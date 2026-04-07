/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

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

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return this.FIRST_NAME; }
    public String getLastName() { return this.LAST_NAME; }
    public String getServicesPhoneNum() { return this.SERVICES_PHONE; }
    public String getInfo() { return this.INFO; }
}
