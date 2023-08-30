package com.putoet.day25;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TuringTest {

    @Test
    void run() {
        final var theTape = new Tape();
        final var turing = new Turing(theTape);

        turing.addState(new State("A", tape -> {
            tape.write(1);
            tape.right();
            return "B";
        }, tape -> {
            tape.write(0);
            tape.left();
            return "B";
        }), true);

        turing.addState(new State("B", tape -> {
            tape.write(1);
            tape.left();
            return "A";
        }, tape -> {
            tape.write(1);
            tape.right();
            return "A";
        }));

        turing.run(6);

        assertEquals(3, theTape.bitsSet());
    }
}