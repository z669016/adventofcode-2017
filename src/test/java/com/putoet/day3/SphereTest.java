package com.putoet.day3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.Point;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    private Sphere sphere;

    @BeforeEach
    void setup() {
        sphere = new Sphere();
    }

    @Test
    void numbersOnRing() {
        assertThrows(AssertionError.class, () -> sphere.numbersOnRing(-1));

        assertEquals(1, sphere.numbersOnRing(0));
        assertEquals(8, sphere.numbersOnRing(1));
        assertEquals(16, sphere.numbersOnRing(2));
        assertEquals(24, sphere.numbersOnRing(3));
        assertEquals(32, sphere.numbersOnRing(4));
        assertEquals(40, sphere.numbersOnRing(5));
    }

    @Test
    void minOnRing() {
        assertThrows(AssertionError.class, () -> sphere.minOnRing(-1));

        assertEquals(1, sphere.minOnRing(0));
        assertEquals(2, sphere.minOnRing(1));
        assertEquals(10, sphere.minOnRing(2));
        assertEquals(26, sphere.minOnRing(3));
        assertEquals(50, sphere.minOnRing(4));
        assertEquals(82, sphere.minOnRing(5));
    }

    @Test
    void maxOnRing() {
        assertThrows(AssertionError.class, () -> sphere.maxOnRing(-1));

        assertEquals(1, sphere.maxOnRing(0));
        assertEquals(9, sphere.maxOnRing(1));
        assertEquals(25, sphere.maxOnRing(2));
        assertEquals(49, sphere.maxOnRing(3));
        assertEquals(81, sphere.maxOnRing(4));
        assertEquals(121, sphere.maxOnRing(5));
    }

    @Test
    void ring() {
        assertThrows(AssertionError.class, () -> sphere.ring(0));

        assertEquals(0, sphere.ring(1));
        assertEquals(1, sphere.ring(2));
        assertEquals(1, sphere.ring(8));
        assertEquals(2, sphere.ring(11));
        assertEquals(5, sphere.ring(96));
        assertEquals(4, sphere.ring(69));
    }

    @Test
    void point() {
        assertEquals(Point.ORIGIN, sphere.point(1));
        assertEquals(Point.of(0, 1), sphere.point(4));
        assertEquals(Point.of(-2, -2), sphere.point(21));
        assertEquals(Point.of(3, 3), sphere.point(31));
        assertEquals(Point.of(-1, 4), sphere.point(62));
        assertEquals(Point.of(-4, 0), sphere.point(69));
        assertEquals(Point.of(5, 2), sphere.point(88));
        assertEquals(Point.of(5, -3), sphere.point(83));

        assertEquals(Point.of(212,-263), sphere.point(277678));
    }

    @Test
    void number() {
        assertEquals(1, sphere.number(Point.ORIGIN));
        assertEquals(4, sphere.number(Point.of(0, 1)));
        assertEquals(21, sphere.number(Point.of(-2, -2)));
        assertEquals(25, sphere.number(Point.of(2, -2)));
        assertEquals(31, sphere.number(Point.of(3, 3)));
        assertEquals(62, sphere.number(Point.of(-1, 4)));
        assertEquals(69, sphere.number(Point.of(-4, 0)));
        assertEquals(88, sphere.number(Point.of(5, 2)));
        assertEquals(83, sphere.number(Point.of(5, -3)));

        assertEquals(277678, sphere.number(Point.of(212,-263)));

        assertEquals(9, sphere.number(Point.of(1, -1)));
    }
}