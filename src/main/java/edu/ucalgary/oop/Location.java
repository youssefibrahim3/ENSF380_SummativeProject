package edu.ucalgary.oop;

public class Location {
    private String name;
    private String address;
    private DisasterVictim[] occupants;
    private Supply[] supplies; 
    private int id;

    /**
     * Constructs a new Location object with the given name and address
     * 
     * @param name The name of the location.
     * @param address The address of the location.
     */
    public Location(String name, String address) {
        this.name = name;
        this.address = address;
        this.occupants = new DisasterVictim[0];
        this.supplies = new Supply[0]; 
    }

    /** 
     * Gets the name of the location.
     * 
     * @return The location's name
     */
    public String getName() {
        return name;
    }

    /** 
     * Sets the name of the location
     * 
     * @param name The new name for the location
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Gets the address of the location
     * 
     * @return The address of the location
     */
    public String getAddress() {
        return address;
    }

    /** 
     * Sets the address of the location
     * 
     * @param address The new address for the location
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** 
     * Gets an array of the occupants at this location
     * 
     * @return An array of occupants at this location
     */
    public DisasterVictim[] getOccupants() {
        return occupants; 
    }

    /** 
     * Sets the array of occupants in this location.
     * 
     * @param occupants The new array of occupants for this location
     */
    public void setOccupants(DisasterVictim[] occupants) {
        // Using clone() for defensive copying: creates a new array with the same elements
        // This prevents external code from modifying the internal array structure
        // Note: This is a SHALLOW copy - the DisasterVictim objects themselves are shared
        this.occupants = occupants != null ? occupants.clone() : new DisasterVictim[0];
    }

    /** 
     * Gets an array of all current supplies at this location.
     * 
     * @return An array of all supplies at this location
     */
    public Supply[] getSupplies() {
        return supplies; 
    }

    /** 
     * Sets the array of all current supplies at this location.
     * 
     * @param supplies An array of supplies to set to this location
     */
    public void setSupplies(Supply[] supplies) {
        // Using clone() for defensive copying: creates a new array with the same elements
        // This prevents external code from modifying the internal array structure
        // Note: This is a SHALLOW copy - the Supply objects themselves are shared
        this.supplies = supplies != null ? supplies.clone() : new Supply[0];
    }

    /** 
     * Gets the associated ID of this location.
     * 
     * @return The location's ID
     */
    public int getId()
    {
        return this.id;
    }
    /** 
     * Sets the associated ID of this location.
     * 
     * @param id The location's new ID
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /** 
     * Adds a new occupant to this location.
     * 
     * @param occupant A DisasterVictim representing a single occupant.
     * @throws IllegalArgumentException if occupant parameter is null
     */
    public void addOccupant(DisasterVictim occupant) {
        if (occupant == null) {
            throw new IllegalArgumentException("Occupant cannot be null");
        }
        
        // Create new array with size + 1
        DisasterVictim[] newOccupants = new DisasterVictim[occupants.length + 1];
        
        // Copy existing occupants
        System.arraycopy(occupants, 0, newOccupants, 0, occupants.length);
        
        // Add new occupant
        newOccupants[occupants.length] = occupant;
        
        // Replace old array
        this.occupants = newOccupants;
    }

    /** 
     * Removes an occupant from this location.
     * 
     * @param occupant A DisasterVictim representing a single occupant.
     * @throws IllegalArgumentException if the provided occupant parameter is null or not found in this location.
     */
    public void removeOccupant(DisasterVictim occupant) throws IllegalArgumentException {
        if (occupant == null) {
            throw new IllegalArgumentException("Occupant cannot be null");
        }
        
        int index = -1;
        for (int i = 0; i < occupants.length; i++) {
            if (occupants[i].equals(occupant)) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            throw new IllegalArgumentException("Occupant not found in location");
        }
        
        DisasterVictim[] newOccupants = new DisasterVictim[occupants.length - 1];
        
        System.arraycopy(occupants, 0, newOccupants, 0, index);
        System.arraycopy(occupants, index + 1, newOccupants, index, occupants.length - index - 1);
        
        this.occupants = newOccupants;
    }

    /** 
     * Adds a new supply to this location.
     * 
     * @param supply The Supply to add to this location's supplies.
     * @throws IllegalArgumentException if supply parameter is null
     */
    public void addSupply(Supply supply) {
        if (supply == null) {
            throw new IllegalArgumentException("Supply cannot be null");
        }
        
        Supply[] newSupplies = new Supply[supplies.length + 1];
        System.arraycopy(supplies, 0, newSupplies, 0, supplies.length);
        
        newSupplies[supplies.length] = supply;
        this.supplies = newSupplies;
    }

    /** 
     * Removes a supply from this location.
     * 
     * @param supply The Supply to remove from this location's supplies.
     * @throws IllegalArgumentException If provided supply is null or not found in this location.
     */
    public void removeSupply(Supply supply) throws IllegalArgumentException {
        if (supply == null) {
            throw new IllegalArgumentException("Supply cannot be null");
        }
        
        int index = -1;
        for (int i = 0; i < supplies.length; i++) {
            if (supplies[i].equals(supply)) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            throw new IllegalArgumentException("Supply not found in location");
        }
        
        Supply[] newSupplies = new Supply[supplies.length - 1];
        System.arraycopy(supplies, 0, newSupplies, 0, index);
        System.arraycopy(supplies, index + 1, newSupplies, index, supplies.length - index - 1);
        
        this.supplies = newSupplies;
    }
}
