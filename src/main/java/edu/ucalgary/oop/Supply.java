/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

import java.time.LocalDate;

public class Supply {
    private String type;
    private int quantity;
    //feature 6
    private boolean perishable;
    private LocalDate expirationDate;

    public Supply(String type, int quantity, boolean perishable, LocalDate expirationDate) throws IllegalArgumentException {
        this.type = type;
        setQuantity(quantity); // Use setter for validation
        this.perishable = perishable;
        setExpirationDate(expirationDate);
    }

    public void setType(String type) { this.type = type; }
    
    public void setQuantity(int quantity) throws IllegalArgumentException {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
    
    public void setExpirationDate(LocalDate expirationDate)
    {
        if (!this.isPerishable())
        {
            throw new IllegalArgumentException("Non-perishable item cannot have expiration date");
        }
        this.expirationDate = expirationDate;
    }
    public boolean isPerishable() { return this.perishable; }
    public LocalDate getExpirationDate() { return this.expirationDate; }
    public String getType() { return this.type; }
    public int getQuantity() { return this.quantity; }

    public boolean isExpired()
    {
        if (this.expirationDate.isBefore(LocalDate.now()) && this.perishable == true)
        {
            return true;
        } else {
            return false;
        }
    }
}
