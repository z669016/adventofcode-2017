package com.putoet.day9;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataStatsTest {
    @Test
    void group1() {
        final var stats = DataStats.parse("{}");
        assertEquals(1, stats.groupScore());
    }

    @Test
    void group2() {
        final var stats = DataStats.parse("{{{}}}");
        assertEquals(6, stats.groupScore());
    }

    @Test
    void group3() {
        final var stats = DataStats.parse("{{},{}}");
        assertEquals(5, stats.groupScore());
    }

    @Test
    void group4() {
        final var stats = DataStats.parse("{{{},{},{{}}}}");
        assertEquals(16, stats.groupScore());
    }

    @Test
    void group5() {
        final var stats = DataStats.parse("{<{},{},{{}}>}");
        assertEquals(1, stats.groupScore());
    }

    @Test
    void group6() {
        final var stats = DataStats.parse("{<a>,<a>,<a>,<a>}");
        assertEquals(1, stats.groupScore());
        assertEquals(4L, stats.garbageLength());
    }

    @Test
    void group7() {
        final var stats = DataStats.parse("{{<a>},{<a>},{<a>},{<a>}}");
        assertEquals(9, stats.groupScore());
    }

    @Test
    void group8() {
        final var stats = DataStats.parse("{{<!>},{<!>},{<!>},{<a>}}");
        assertEquals(3, stats.groupScore());
        assertEquals(19L, stats.garbageLength());
    }

    @Test
    void group9() {
        final var stats = DataStats.parse("{{<!!>},{<!!>},{<!!>},{<!!>}}");
        assertEquals(9, stats.groupScore());
    }

    @Test
    void group10() {
        final var stats = DataStats.parse("{{<a!>},{<a!>},{<a!>},{<ab>}}");
        assertEquals(3, stats.groupScore());
    }
}