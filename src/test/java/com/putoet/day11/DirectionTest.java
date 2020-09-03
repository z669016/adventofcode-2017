package com.putoet.day11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {
    @Test
    void direction() {
        assertEquals(Direction.NORTH, Direction.of("n"));
        assertEquals(Direction.NORTH_EAST, Direction.of("ne"));
        assertEquals(Direction.SOUTH_EAST, Direction.of("se"));
        assertEquals(Direction.SOUTH, Direction.of("s"));
        assertEquals(Direction.SOUTH_WEST, Direction.of("sw"));
        assertEquals(Direction.NORTH_WEST, Direction.of("nw"));

        assertThrows(IllegalArgumentException.class, () -> Direction.of("bla"));
    }
}