package com.putoet.day16;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DanceLineTest {
    private static final String LINE = "abcde";
    private DanceLine danceLine;

    @BeforeEach
    void setup() {
        danceLine = new DanceLine(LINE);
    }

    @Test
    void create() {
        assertThrows(AssertionError.class, () -> new DanceLine(""));
    }

    @Test
    void moves() {
        // assertEquals("cdeab", danceLine.spin(3).toString());
        assertEquals("eabcd", danceLine.spin(1).toString());
        assertEquals("eabdc", danceLine.exchange(3, 4).toString());
        assertEquals("baedc", danceLine.partner('e', 'b').toString());
    }

    @Test
    void dance() {
        final var moves = List.of(
                "s1",
                "x3/4",
                "pe/b"
        );
        danceLine.dance(moves);
        assertEquals("baedc", danceLine.toString());

        danceLine.dance(moves);
        assertEquals("ceadb", danceLine.toString());
    }

    @Test
    void testToString() {
        assertEquals(LINE, danceLine.toString());
    }
}