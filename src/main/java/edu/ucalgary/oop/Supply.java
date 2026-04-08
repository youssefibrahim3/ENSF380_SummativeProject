package edu.ucalgary.oop;

import java.time.LocalDate;

/**
 * Class representing a single supply
 * 
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-04-02
 */
public class Supply {
    private String type;
    private int quantity;
    //feature 6
    private boolean perishable;
    private LocalDate expirationDate;

    private int id;

    private int location_id = -1;
    private int victim_id = -1;
    private String description;

    public Supply(String type, int quantity, boolean perishable, LocalDate expirationDate, int location_id, int victim_id, String description) throws IllegalArgumentException {
        this.type = type;
        setQuantity(quantity); // Use setter for validation
        this.perishable = perishable;
        setExpirationDate(expirationDate);
        this.location_id = location_id;
        this.victim_id = victim_id;
        this.description = description;
    }

    public void setType(String type) { this.type = type; }
    
    /** 
     * @param quantity
     * @throws IllegalArgumentException
     */
    public void setQuantity(int quantity) throws IllegalArgumentException {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
    
    /** 
     * @param expirationDate
     */
    public void setExpirationDate(LocalDate expirationDate)
    {
        if (!this.perishable && expirationDate != null) 
        {
            throw new IllegalArgumentException("Non-perishable item cannot have expiration date");
        }
        if (this.perishable && expirationDate == null)
        {
            throw new IllegalArgumentException("Perishable items must have an expiration date");
        }
        this.expirationDate = expirationDate;
    }
    public boolean isPerishable() { return this.perishable; }
    public LocalDate getExpirationDate() { return this.expirationDate; }
    public String getType() { return this.type; }
    public int getQuantity() { return this.quantity; }
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    /** 
     * @return int
     */
    public int getLocationId()
    {
        return this.location_id;
    }
    /** 
     * @param location_id
     */
    public void setLocationId(int location_id)
    {
        if (location_id < 0)
        {
            throw new IllegalArgumentException("Location ID cannot be negative");
        }
        this.location_id = location_id;
    }

    /** 
     * @return int
     */
    public int getVictimId()
    {
        return this.victim_id;
    }
    /** 
     * @param victim_id
     */
    public void setVictimId(int victim_id)
    {
        if (victim_id < 0)
        {
            throw new IllegalArgumentException("Victim ID cannot be negative");
        }
        this.victim_id = victim_id;
    }

    /** 
     * @return String
     */
    public String getDescription()
    {
        return this.description;
    }
    /** 
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /** 
     * @return boolean
     */
    public boolean isExpired() {
        if (!this.perishable || this.expirationDate == null) return false;
        return this.expirationDate.isBefore(LocalDate.now());
    }
}
