package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class MedicalRecordTest {

    private Location location;

    @Before
    public void setUp() {
        location = new Location("Test Shelter", "123 Test St");
    }

    @Test
    public void testConstructorSetsTreatmentDetails() {
        MedicalRecord r = new MedicalRecord(location, "broken arm", LocalDate.now());
        assertEquals("broken arm", r.getTreatmentDetails());
    }

    @Test
    public void testConstructorSetsDate() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        MedicalRecord r = new MedicalRecord(location, "treatment", date);
        assertEquals(date, r.getDateOfTreatment());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFutureDateThrows() {
        new MedicalRecord(location, "treatment", LocalDate.now().plusDays(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDateThrows() {
        new MedicalRecord(location, "treatment", null);
    }

    @Test
    public void testSetTreatmentDetails() {
        MedicalRecord r = new MedicalRecord(location, "old", LocalDate.now());
        r.setTreatmentDetails("new details");
        assertEquals("new details", r.getTreatmentDetails());
    }

    @Test
    public void testSetLocation() {
        MedicalRecord r = new MedicalRecord(location, "treatment", LocalDate.now());
        Location newLoc = new Location("New", "456 St");
        r.setLocation(newLoc);
        assertEquals("New", r.getLocation().getName());
    }

    @Test
    public void testConstructorSetsLocation() {
        MedicalRecord r = new MedicalRecord(location, "treatment", LocalDate.now());
        assertEquals(location, r.getLocation());
    }
}