package edu.ucalgary.oop;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class VictimController {
    private VictimDAO victimDAO;
    private Scanner scanner;

    public VictimController(VictimDAO victimDAO, Scanner scanner)
    {
        this.victimDAO = victimDAO;
        this.scanner = scanner;
    }


}
