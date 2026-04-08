package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.List;

public class SupplyController {
    private SupplyDAO supplyDAO;

    public SupplyController(SupplyDAO supplyDAO)
    {
        this.supplyDAO = supplyDAO;
    }

    public void createSupply(String type, int locationId, int victimId,
                            LocalDate expiryDate, String description)
    {

    }
}
