package com.putoet.day25;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

class State implements Function<Tape,String> {
    private final String name;
    private final Function<Tape,String> onZero;
    private final Function<Tape,String> onOne;

    public State(String name, Function<Tape, String> onZero, Function<Tape, String> onOne) {
        this.name = name;
        this.onZero = onZero;
        this.onOne = onOne;
    }

    public String name() { return name; }

    public String apply(@NotNull Tape tape) {
        final var bit = tape.read();
        return bit == 0 ? onZero.apply(tape) : onOne.apply(tape);
    }
}
