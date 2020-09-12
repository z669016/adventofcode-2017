package com.putoet.day25;

import java.util.function.Function;

public class State {
    private final String name;
    private final Function<Tape,String> onZero;
    private final Function<Tape,String> onOne;

    public State(String name, Function<Tape, String> onZero, Function<Tape, String> onOne) {
        this.name = name;
        this.onZero = onZero;
        this.onOne = onOne;
    }

    public String name() { return name; }

    public String apply(Tape tape) {
        final int bit = tape.read();
        return bit == 0 ? onZero.apply(tape) : onOne.apply(tape);
    }
}
