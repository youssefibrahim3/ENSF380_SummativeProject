package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class FamilyRelationTest {

    private DisasterVictim personOne;
    private DisasterVictim personTwo;

    @Before
    public void setUp() {
        personOne = new DisasterVictim("Alice", LocalDate.now());
        personTwo = new DisasterVictim("Bob", LocalDate.now());
    }

    @Test
    public void testConstructorSetsRelationshipType() {
        FamilyRelation r = new FamilyRelation(personOne, "sibling", personTwo);
        assertEquals("sibling", r.getRelationshipTo());
    }

    @Test
    public void testConstructorSetsPersonOne() {
        FamilyRelation r = new FamilyRelation(personOne, "sibling", personTwo);
        assertEquals(personOne, r.getPersonOne());
    }

    @Test
    public void testConstructorSetsPersonTwo() {
        FamilyRelation r = new FamilyRelation(personOne, "sibling", personTwo);
        assertEquals(personTwo, r.getPersonTwo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPersonOneThrows() {
        new FamilyRelation(null, "sibling", personTwo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPersonTwoThrows() {
        new FamilyRelation(personOne, "sibling", null);
    }

    @Test
    public void testSetRelationshipTo() {
        FamilyRelation r = new FamilyRelation(personOne, "sibling", personTwo);
        r.setRelationshipTo("parent");
        assertEquals("parent", r.getRelationshipTo());
    }
}