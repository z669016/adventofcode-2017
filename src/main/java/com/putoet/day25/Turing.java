package com.putoet.day25;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

class Turing {
    private final Tape tape;
    private final Map<String, State> states = new HashMap<>();
    private State current;

    public Turing(@NotNull Tape tape) {
        this.tape = tape;
    }

    public void addState(@NotNull State state) {
        addState(state, false);
    }

    public void addState(@NotNull State state, boolean start) {
        states.put(state.name(), state);

        if (start)
            current = state;
    }

    public void run(int count) {
        for (var i = 0; i < count; i++) {
            final var next = current.apply(tape);
            current = states.get(next);
        }
    }

    @Override
    public String toString() {
        return String.format("{States: %s, current state: %s, tape: %s}", states.keySet(), current.name(), tape);
    }
}
