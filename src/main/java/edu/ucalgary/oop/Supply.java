package edu.ucalgary.oop;

import java.time.LocalDate;

/**
 * Class representing a single supply
 * 
 * @author Youssef Ibrahim
 * @version 2.0
 * @since 2026-03-30
 */
public class Supply {
    private String type;
    private int quantity;
    //feature 6
    private boolean perishable;
    private LocalDate expirationDate;

    private int id;

    private int location_id = 0;
    private int victim_id = 0;
    private String description;

    /**
     * Constructs a new Supply with the specified attributes.
     * 
     * @param type The type of supply
     * @param quantity The quantity of this supply available
     * @param perishable Whether the supply is perishable if not
     * @param expirationDate The expiration date of the supply (if perishable)
     * @param location_id The id for the location of the supply
     * @param victim_id The id for the victim the supply is assigned to
     * @param description A description of the supply
     * @throws IllegalArgumentException 
     */
    public Supply(String type, int quantity, boolean perishable, LocalDate expirationDate, int location_id, int victim_id, String description) throws IllegalArgumentException {
        this.type = type;
        setQuantity(quantity); // Use setter for validation
        this.perishable = perishable;
        setExpirationDate(expirationDate);
        this.location_id = location_id;
        this.victim_id = victim_id;
        this.description = description;
    }
    
    /** 
     * Sets the expiration date of the supply.
     * 
     * @param expirationDate The expiration date of the supply
     * @throws IllegalArgumentException if the supply is perishable and has no expiration date, or if it is perishable and has an expiration date.
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

    /** 
     * Gets the expiration date of the supply.
     * 
     * @return The expiration date of the supply
     */
    public LocalDate getExpirationDate() 
    { 
        return this.expirationDate; 
    }

    /** 
     * Gets whether the supply is perishable or not
     * 
     * @return If supply is perishable
     */
    public boolean isPerishable() 
    { 
        return this.perishable; 
    }

    public String getType() { return this.type; }
    /**
     * Sets the type of supply.
     * 
     * @param type The type of supply
     */
    public void setType(String type) 
    { 
        this.type = type; 
    }

    /** 
     * Sets the quantity of this supply.
     * 
     * @param quantity The quantity of the supply
     * @throws IllegalArgumentException if the quantity is a negative value
     */
    public void setQuantity(int quantity) throws IllegalArgumentException 
    {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    /** 
     * Gets the quantity of the supply.
     * 
     * @return The quantity of the supply
     */
    public int getQuantity() 
    { 
        return this.quantity; 
    }

    /** 
     * Gets the ID of the supply.
     * 
     * @return The ID of the supply
     */
    public int getId() 
    { 
        return this.id; 
    }

    /** 
     * Sets the ID of the supply.
     * 
     * @param id The ID of the supply
     */
    public void setId(int id) 
    { 
        this.id = id; 
    }
    
    /** 
     * Gets the supply's location ID
     * 
     * @return The supply's location ID
     */
    public int getLocationId()
    {
        return this.location_id;
    }

    /** 
     * Set's the supply's location ID
     * 
     * @param location_id The supply's location ID
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
     * Gets the supply's victim ID
     * 
     * @return The supply's victim ID
     */
    public int getVictimId()
    {
        return this.victim_id;
    }
    /** 
     * Sets the supply's victim ID
     * 
     * @param victim_id The supply's victim ID
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
     * Gets the description of the supply.
     * 
     * @return The supply description
     */
    public String getDescription()
    {
        return this.description;
    }
    /** 
     * Sets the description of the supply.
     * 
     * @param description The supply description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /** 
     * Gets whether the supply is expired or not, if it is perishable
     * 
     * @return True if the supply is expired, false if it is not, or if it is non-perishable
     */
    public boolean isExpired() {
        if (!this.perishable || this.expirationDate == null) return false;
        return this.expirationDate.isBefore(LocalDate.now());
    }
}
