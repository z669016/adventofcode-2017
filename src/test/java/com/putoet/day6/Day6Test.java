package com.putoet.day6;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {
    private final List<Integer> memoryBanks = List.of(0, 2, 7, 0);

    @Test
    void redistribute() {
        List<Integer> next = Day6.redistribute(memoryBanks);
        assertEquals(List.of(2, 4, 1, 2), next);

        next = Day6.redistribute(next);
        assertEquals(List.of(3, 1, 2, 3), next);

        next = Day6.redistribute(next);
        assertEquals(List.of(0, 2, 3, 4), next);

        next = Day6.redistribute(next);
        assertEquals(List.of(1, 3, 4, 1), next);

        next = Day6.redistribute(next);
        assertEquals(List.of(2, 4, 1, 2), next);
    }

    @Test
    void reoccurrenceAfter() {
        assertEquals(5, Day6.reoccurrenceAfter(memoryBanks));
    }

    @Test
    void reoccurrenceCount() {
        assertEquals(4, Day6.reoccurrenceCount(memoryBanks));
    }
}