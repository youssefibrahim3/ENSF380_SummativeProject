package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class SupplyTest {

    @Test
    public void testPerishableConstructorValid() {
        Supply s = new Supply("water", 0, true, LocalDate.now().plusDays(10), 1, 0, "test");
        assertTrue(s.isPerishable());
    }

    @Test
    public void testNonPerishableConstructorValid() {
        Supply s = new Supply("blanket", 0, false, null, 1, 0, "test");
        assertFalse(s.isPerishable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonPerishableWithExpiryThrows() {
        new Supply("blanket", 0, false, LocalDate.now().plusDays(10), 1, 0, "test");
    }

    @Test
    public void testIsExpiredWhenPastDate() {
        Supply s = new Supply("water", 0, true, LocalDate.now().minusDays(1), 1, 0, "test");
        assertTrue(s.isExpired());
    }

    @Test
    public void testIsNotExpiredWhenFutureDate() {
        Supply s = new Supply("water", 0, true, LocalDate.now().plusDays(10), 1, 0, "test");
        assertFalse(s.isExpired());
    }

    @Test
    public void testNonPerishableNeverExpired() {
        Supply s = new Supply("blanket", 0, false, null, 1, 0, "test");
        assertFalse(s.isExpired());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeQuantityThrows() {
        new Supply("water", -1, true, LocalDate.now().plusDays(1), 1, 0, "test");
    }

    @Test
    public void testGetType() {
        Supply s = new Supply("blanket", 0, false, null, 1, 0, "test");
        assertEquals("blanket", s.getType());
    }

    @Test
    public void testSetType() {
        Supply s = new Supply("blanket", 0, false, null, 1, 0, "test");
        s.setType("pillow");
        assertEquals("pillow", s.getType());
    }

    @Test
    public void testGetLocationId() {
        Supply s = new Supply("blanket", 0, false, null, 5, 0, "test");
        assertEquals(5, s.getLocationId());
    }

    @Test
    public void testSetVictimId() {
        Supply s = new Supply("blanket", 0, false, null, 1, 0, "test");
        s.setVictimId(3);
        assertEquals(3, s.getVictimId());
    }

    @Test
    public void testExpiryDateStoredCorrectly() {
        LocalDate expiry = LocalDate.of(2027, 1, 1);
        Supply s = new Supply("water", 0, true, expiry, 1, 0, "test");
        assertEquals(expiry, s.getExpirationDate());
    }
}