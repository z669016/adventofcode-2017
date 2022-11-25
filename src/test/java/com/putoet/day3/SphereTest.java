package com.putoet.day3;

import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    @Test
    void numbersOnRing() {
        assertThrows(AssertionError.class, () -> Sphere.numbersOnRing(-1));

        assertEquals(1, Sphere.numbersOnRing(0));
        assertEquals(8, Sphere.numbersOnRing(1));
        assertEquals(16, Sphere.numbersOnRing(2));
        assertEquals(24, Sphere.numbersOnRing(3));
        assertEquals(32, Sphere.numbersOnRing(4));
        assertEquals(40, Sphere.numbersOnRing(5));
    }

    @Test
    void minOnRing() {
        assertThrows(AssertionError.class, () -> Sphere.minOnRing(-1));

        assertEquals(1, Sphere.minOnRing(0));
        assertEquals(2, Sphere.minOnRing(1));
        assertEquals(10, Sphere.minOnRing(2));
        assertEquals(26, Sphere.minOnRing(3));
        assertEquals(50, Sphere.minOnRing(4));
        assertEquals(82, Sphere.minOnRing(5));
    }

    @Test
    void maxOnRing() {
        assertThrows(AssertionError.class, () -> Sphere.maxOnRing(-1));

        assertEquals(1, Sphere.maxOnRing(0));
        assertEquals(9, Sphere.maxOnRing(1));
        assertEquals(25, Sphere.maxOnRing(2));
        assertEquals(49, Sphere.maxOnRing(3));
        assertEquals(81, Sphere.maxOnRing(4));
        assertEquals(121, Sphere.maxOnRing(5));
    }

    @Test
    void ring() {
        assertThrows(AssertionError.class, () -> Sphere.ring(0));

        assertEquals(0, Sphere.ring(1));
        assertEquals(1, Sphere.ring(2));
        assertEquals(1, Sphere.ring(8));
        assertEquals(2, Sphere.ring(11));
        assertEquals(5, Sphere.ring(96));
        assertEquals(4, Sphere.ring(69));
    }

    @Test
    void point() {
        assertEquals(Point.ORIGIN, Sphere.point(1));
        assertEquals(Point.of(0, 1), Sphere.point(4));
        assertEquals(Point.of(-2, -2), Sphere.point(21));
        assertEquals(Point.of(3, 3), Sphere.point(31));
        assertEquals(Point.of(-1, 4), Sphere.point(62));
        assertEquals(Point.of(-4, 0), Sphere.point(69));
        assertEquals(Point.of(5, 2), Sphere.point(88));
        assertEquals(Point.of(5, -3), Sphere.point(83));

        assertEquals(Point.of(212,-263), Sphere.point(277678));
    }

    @Test
    void number() {
        assertEquals(1, Sphere.number(Point.ORIGIN));
        assertEquals(4, Sphere.number(Point.of(0, 1)));
        assertEquals(21, Sphere.number(Point.of(-2, -2)));
        assertEquals(25, Sphere.number(Point.of(2, -2)));
        assertEquals(31, Sphere.number(Point.of(3, 3)));
        assertEquals(62, Sphere.number(Point.of(-1, 4)));
        assertEquals(69, Sphere.number(Point.of(-4, 0)));
        assertEquals(88, Sphere.number(Point.of(5, 2)));
        assertEquals(83, Sphere.number(Point.of(5, -3)));

        assertEquals(277678, Sphere.number(Point.of(212,-263)));

        assertEquals(9, Sphere.number(Point.of(1, -1)));
    }
}