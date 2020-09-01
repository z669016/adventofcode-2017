package com.putoet.day5;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JumperTest {
    @Test
    void create() {
        assertThrows(AssertionError.class, () -> new Jumper(null));
        assertThrows(AssertionError.class, () -> new Jumper(List.of()));
    }

    @Test
    void increase() {
        final Jumper jumper = new Jumper(List.of(0, 3, 0, 1, -3));
        assertEquals(5, jumper.run());
    }

    @Test
    void decrease() {
        final Jumper jumper = new Jumper(List.of(0, 3, 0, 1, -3), true);
        assertEquals(10, jumper.run());
    }
}