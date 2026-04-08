package edu.ucalgary.oop;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;

/**
 * "Victim" controller for interaction between console UI and DAO
 *
 * 
 * @author Youssef Ibrahim
 * @version 1.0
 * @since 2026-04-08
 */

public class VictimController {
    private VictimDAO victimDAO;

    public VictimController(VictimDAO victimDAO)
    {
        this.victimDAO = victimDAO;
    }

    public DisasterVictim pickVictim(String prompt)
    {
        List<DisasterVictim> victims = victimDAO.getAll();
        if (victims.isEmpty())
        {
            
        }
    }
}
