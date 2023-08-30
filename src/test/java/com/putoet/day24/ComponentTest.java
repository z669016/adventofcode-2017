package com.putoet.day24;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComponentTest {

    @Test
    void reverse() {
        final var c = new Component(0, 1, 2);
        assertEquals(1, c.one());
        assertEquals(2, c.two());

        c.reverse();

        assertEquals(2, c.one());
        assertEquals(1, c.two());
    }

    @Test
    void hasPort() {
        final var c = new Component(0, 1, 2);
        assertFalse(c.hasPort(0));
        assertTrue(c.hasPort(1));
        assertTrue(c.hasPort(2));
    }

    @Test
    void testEquals() {
        final var c1 = new Component(0, 1, 2);
        final var c2 = new Component(0, 2, 3);
        final var c3 = new Component(1, 3, 2);

        assertEquals(c2, c1);
        assertNotEquals(c3, c1);
    }
}