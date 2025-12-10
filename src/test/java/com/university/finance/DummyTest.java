package com.university.finance;

import org.junit.Test;
import static org.junit.Assert.*;

public class DummyTest {

    @Test
    public void testAlwaysPass() {
        assertTrue(true);
    }

    @Test
    public void testAddition() {
        int result = 2 + 2;
        assertEquals(4, result);
    }

    @Test
    public void testString() {
        String text = "Hello";
        assertNotNull(text);
    }
}