package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class CulturalOptionsTest {

    private CulturalOptions options;

    @Before
    public void setUp() {
        HashMap<String, Set<String>> map = new HashMap<>();
        map.put("dietary restrictions", new HashSet<>(Arrays.asList("halal", "vegetarian")));
        map.put("safe-space requirements", new HashSet<>(Arrays.asList("LGBTQIA+ affirming")));
        options = new CulturalOptions(map);
    }

    @Test
    public void testGetAccommodationsNotNull() {
        assertNotNull(options.getAccommodations());
    }

    @Test
    public void testGetAccommodationsContainsExpectedKey() {
        assertTrue(options.getAccommodations().containsKey("dietary restrictions"));
    }

    @Test
    public void testGetAccommodationsContainsExpectedOption() {
        assertTrue(options.getAccommodations().get("dietary restrictions").contains("halal"));
    }

    @Test
    public void testGetAccommodationsSize() {
        assertEquals(2, options.getAccommodations().size());
    }
}