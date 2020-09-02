package com.putoet.day9;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupDataStatsTest {

    @Test
    void emptyGarbage() {
        final String line = "<>";
        equalsLineList(line);
    }

    @Test
    void randomGarbage() {
        final String line = "<random characters>";
        equalsLineList(line);
    }

    @Test
    void nestedGarbage() {
        final String line = "<<<<<>";
        equalsLineList(line);
    }

    @Test
    void ignoredGarbage() {
        final String line = "<{!>}>";
        equalsLineList(line);
    }

    @Test
    void excludedGarbage() {
        final String line = "<{!>}>";
        equalsLineList(line);
    }

    @Test
    void doubleExcludedGarbage() {
        final String line = "<!!!>>";
        equalsLineList(line);
    }

    @Test
    void complicatedGarbage() {
        final String line = "<{o\"i!a,<{i<a>";
        equalsLineList(line);
    }

    @Test
    void group1() {
        final GroupDataStats stats = parse("{}");
        assertEquals(1, stats.groupCounter());
        assertEquals(1, stats.groupScore());
    }

    @Test
    void group2() {
        final GroupDataStats stats = parse("{{{}}}");
        assertEquals(3, stats.groupCounter());
        assertEquals(6, stats.groupScore());
    }

    @Test
    void group3() {
        final GroupDataStats stats = parse("{{},{}}");
        assertEquals(3, stats.groupCounter());
        assertEquals(5, stats.groupScore());
    }

    @Test
    void group4() {
        final GroupDataStats stats = parse("{{{},{},{{}}}}");
        assertEquals(6, stats.groupCounter());
        assertEquals(16, stats.groupScore());
    }

    @Test
    void group5() {
        final GroupDataStats stats = parse("{<{},{},{{}}>}");
        assertEquals(1, stats.groupCounter());
        assertEquals(1, stats.groupScore());
    }

    @Test
    void group6() {
        final GroupDataStats stats = parse("{<a>,<a>,<a>,<a>}");
        assertEquals(1, stats.groupCounter());
        assertEquals(1, stats.groupScore());
        assertEquals(4L, stats.garbageLength());
    }

    @Test
    void group7() {
        final GroupDataStats stats = parse("{{<a>},{<a>},{<a>},{<a>}}");
        assertEquals(5, stats.groupCounter());
        assertEquals(9, stats.groupScore());
    }

    @Test
    void group8() {
        final GroupDataStats stats = parse("{{<!>},{<!>},{<!>},{<a>}}");
        assertEquals(2, stats.groupCounter());
        assertEquals(3, stats.groupScore());
        assertEquals(19L, stats.garbageLength());
    }

    @Test
    void group9() {
        final GroupDataStats stats = parse("{{<!!>},{<!!>},{<!!>},{<!!>}}");
        assertEquals(5, stats.groupCounter());
        assertEquals(9, stats.groupScore());
    }

    @Test
    void group10() {
        final GroupDataStats stats = parse("{{<a!>},{<a!>},{<a!>},{<ab>}}");
        assertEquals(2, stats.groupCounter());
        assertEquals(3, stats.groupScore());
    }

    private void equalsLineList(String line) {
        final GroupDataStats stats = parse(line);
        assertEquals(List.of(line), stats.garbageList());
    }

    private GroupDataStats parse(String line) {
        final GroupData data = new GroupData(line);
        final GroupDataStats stats = new GroupDataStats(data);
        stats.parse();

        return stats;
    }

}