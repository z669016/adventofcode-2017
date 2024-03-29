package com.putoet.day25;

import com.putoet.utils.Timer;

public class Day25 {
    public static void main(String[] args) {
        Timer.run(() -> {
            final var tape = new Tape();
            final var turing = new Turing(tape);

            turing.addState(a(), true);
            turing.addState(b());
            turing.addState(c());
            turing.addState(d());
            turing.addState(e());
            turing.addState(f());

            turing.run(12964419);

            System.out.println("Diagnostic checksum is " + tape.bitsSet());
        });
    }

    private static State a() {
        return new State("A", tape -> {
            tape.write(1);
            tape.right();
            return "B";
        }, tape -> {
            tape.write(0);
            tape.right();
            return "F";
        });
    }

    private static State b() {
        return new State("B", tape -> {
            tape.write(0);
            tape.left();
            return "B";
        }, tape -> {
            tape.write(1);
            tape.left();
            return "C";
        });
    }

    private static State c() {
        return new State("C", tape -> {
            tape.write(1);
            tape.left();
            return "D";
        }, tape -> {
            tape.write(0);
            tape.right();
            return "C";
        });
    }

    private static State d() {
        return new State("D", tape -> {
            tape.write(1);
            tape.left();
            return "E";
        }, tape -> {
            tape.write(1);
            tape.right();
            return "A";
        });
    }

    private static State e() {
        return new State("E", tape -> {
            tape.write(1);
            tape.left();
            return "F";
        }, tape -> {
            tape.write(0);
            tape.left();
            return "D";
        });
    }

    private static State f() {
        return new State("F", tape -> {
            tape.write(1);
            tape.right();
            return "A";
        }, tape -> {
            tape.write(0);
            tape.left();
            return "E";
        });
    }
}
