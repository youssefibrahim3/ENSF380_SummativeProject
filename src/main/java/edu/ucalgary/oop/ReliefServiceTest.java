package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class ReliefServiceTest {

    private Inquirer inquirer;
    private DisasterVictim victim;
    private Location location;

    @Before
    public void setUp() {
        inquirer = new Inquirer("Joe", "Smith", null, "info");
        victim = new DisasterVictim("Jane", LocalDate.now());
        location = new Location("Shelter", "123 St");
    }

    @Test
    public void testConstructorSetsInfoProvided() {
        ReliefService rs = new ReliefService(inquirer, victim, LocalDate.now(), "details", location);
        assertEquals("details", rs.getInfoProvided());
    }

    @Test
    public void testConstructorSetsInquirer() {
        ReliefService rs = new ReliefService(inquirer, victim, LocalDate.now(), "details", location);
        assertEquals(inquirer, rs.getInquirer());
    }

    @Test
    public void testConstructorSetsMissingPerson() {
        ReliefService rs = new ReliefService(inquirer, victim, LocalDate.now(), "details", location);
        assertEquals(victim, rs.getMissingPerson());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFutureDateThrows() {
        new ReliefService(inquirer, victim, LocalDate.now().plusDays(1), "details", location);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDateThrows() {
        new ReliefService(inquirer, victim, null, "details", location);
    }

    @Test
    public void testSetInfoProvided() {
        ReliefService rs = new ReliefService(inquirer, victim, LocalDate.now(), "old", location);
        rs.setInfoProvided("new info");
        assertEquals("new info", rs.getInfoProvided());
    }

    @Test
    public void testSetId() {
        ReliefService rs = new ReliefService(inquirer, victim, LocalDate.now(), "details", location);
        rs.setId(7);
        assertEquals(7, rs.getId());
    }
}