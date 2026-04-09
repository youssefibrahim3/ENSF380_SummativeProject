package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class InquirerTest {

    private Inquirer inquirer;

    @Before
    public void setUp() {
        inquirer = new Inquirer("Joe", "Smith", "555-1234", "Looking for family");
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Joe", inquirer.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Smith", inquirer.getLastName());
    }

    @Test
    public void testGetPhone() {
        assertEquals("555-1234", inquirer.getServicesPhoneNum());
    }

    @Test
    public void testGetInfo() {
        assertEquals("Looking for family", inquirer.getInfo());
    }

    @Test
    public void testSetAndGetId() {
        inquirer.setId(10);
        assertEquals(10, inquirer.getId());
    }
}