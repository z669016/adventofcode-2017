package com.putoet.day25;

import com.putoet.day25.Tape;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TapeTest {

    @Test
    void blocks() {
        final Tape tape = new Tape();

        assertEquals(1, tape.blocks());
        tape.left();
        assertEquals(2, tape.blocks());

        for (int idx = 0; idx < 64; idx++)
            tape.left();
        assertEquals(3, tape.blocks());

        for (int idx = 0; idx < (3 * 64 + 1); idx++)
            tape.right();

        assertEquals(5, tape.blocks());
    }

    @Test
    void readWrite() {
        final Tape tape = new Tape();
        for (int idx = 0; idx < 3 * 64; idx++) {
            tape.write( idx % 2);
            tape.right();
        }
        for (int idx = 5 * 64; idx > 0; idx--) {
            tape.write( idx % 2);
            tape.left();
        }

        assertEquals(5 * 32, tape.bitsSet());
    }
}