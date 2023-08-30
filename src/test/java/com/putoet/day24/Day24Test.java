package com.putoet.day24;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    @Test
    void bridges() {
        final var bridges = Day24.bridges();
        final var strongest = bridges.stream().mapToInt(Bridge::strength).max().orElseThrow();
        assertEquals(31, strongest);
    }
}