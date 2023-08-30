package com.putoet.day25;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TapeTest {

    @Test
    void blocks() {
        final var tape = new Tape();

        assertEquals(1, tape.blocks());
        tape.left();
        assertEquals(2, tape.blocks());

        for (var idx = 0; idx < 64; idx++)
            tape.left();
        assertEquals(3, tape.blocks());

        for (var idx = 0; idx < (3 * 64 + 1); idx++)
            tape.right();

        assertEquals(5, tape.blocks());
    }

    @Test
    void readWrite() {
        final var tape = new Tape();
        for (var idx = 0; idx < 3 * 64; idx++) {
            tape.write( idx % 2);
            tape.right();
        }
        for (var idx = 5 * 64; idx > 0; idx--) {
            tape.write( idx % 2);
            tape.left();
        }

        assertEquals(5 * 32, tape.bitsSet());
    }
}