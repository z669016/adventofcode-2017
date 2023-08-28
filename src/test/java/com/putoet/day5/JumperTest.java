package com.putoet.day5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JumperTest {
    @Test
    void create() {
        assertThrows(AssertionError.class, () -> new Jumper(null));
        assertThrows(AssertionError.class, () -> new Jumper(new int[] {}));
    }

    @Test
    void increase() {
        final Jumper jumper = new Jumper(new int[] {0, 3, 0, 1, -3});
        assertEquals(5, jumper.run());
    }

    @Test
    void decrease() {
        final Jumper jumper = new Jumper(new int[] {0, 3, 0, 1, -3}, true);
        assertEquals(10, jumper.run());
    }
}