package com.putoet.day24;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    @Test
    void bridges() {
        final List<Bridge> bridges = Day24.bridges();
        bridges.forEach(System.out::println);

        final int strongest = bridges.stream().mapToInt(Bridge::strength).max().orElseThrow();

        assertEquals(31, strongest);
    }
}