package com.putoet.day15;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {
    @Test
    void get() {
        final var a = new Generator(16807, 65);
        final var b = new Generator(48271, 8921);

        assertEquals(1092455, a.get());
        assertEquals(1181022009, a.get());
        assertEquals(245556042, a.get());
        assertEquals(1744312007, a.get());
        assertEquals(1352636452, a.get());

        assertEquals(430625591, b.get());
        assertEquals(1233683848, b.get());
        assertEquals(1431495498, b.get());
        assertEquals(137874439, b.get());
        assertEquals(285222916, b.get());
    }
}