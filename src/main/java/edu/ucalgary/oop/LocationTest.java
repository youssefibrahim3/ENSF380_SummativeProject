package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class LocationTest {

    private Location location;

    @Before
    public void setUp() {
        location = new Location("Shelter A", "123 Main St");
    }

    @Test
    public void testConstructorSetsName() {
        assertEquals("Shelter A", location.getName());
    }

    @Test
    public void testConstructorSetsAddress() {
        assertEquals("123 Main St", location.getAddress());
    }

    @Test
    public void testSetName() {
        location.setName("Shelter B");
        assertEquals("Shelter B", location.getName());
    }

    @Test
    public void testSetAddress() {
        location.setAddress("456 Oak Ave");
        assertEquals("456 Oak Ave", location.getAddress());
    }

    @Test
    public void testAddOccupantIncreasesCount() {
        DisasterVictim v = new DisasterVictim("John", LocalDate.now());
        location.addOccupant(v);
        assertEquals(1, location.getOccupants().length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullOccupantThrows() {
        location.addOccupant(null);
    }

    @Test
    public void testRemoveOccupantDecreasesCount() {
        DisasterVictim v = new DisasterVictim("John", LocalDate.now());
        location.addOccupant(v);
        location.removeOccupant(v);
        assertEquals(0, location.getOccupants().length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonexistentOccupantThrows() {
        DisasterVictim v = new DisasterVictim("John", LocalDate.now());
        location.removeOccupant(v);
    }

    @Test
    public void testAddSupplyIncreasesCount() {
        Supply s = new Supply("blanket", 0, false, null, 1, 0, "test");
        location.addSupply(s);
        assertEquals(1, location.getSupplies().length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullSupplyThrows() {
        location.addSupply(null);
    }

    @Test
    public void testRemoveSupplyDecreasesCount() {
        Supply s = new Supply("blanket", 0, false, null, 1, 0, "test");
        location.addSupply(s);
        location.removeSupply(s);
        assertEquals(0, location.getSupplies().length);
    }

    @Test
    public void testSetId() {
        location.setId(5);
        assertEquals(5, location.getId());
    }
}