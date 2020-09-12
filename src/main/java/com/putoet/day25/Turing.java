package com.putoet.day25;

import java.util.HashMap;
import java.util.Map;

public class Turing {
    private final Tape tape;
    private final Map<String, State> states = new HashMap<>();
    private State current;

    public Turing(Tape tape) {
        this.tape = tape;
    }

    public void addState(State state) {
        addState(state, false);
    }

    public void addState(State state, boolean start) {
        states.put(state.name(), state);

        if (start)
            current = state;
    }

    public void setState(String name) {
        current = states.get(name);
    }

    public void run(int count) {
        for (int i = 0; i < count; i++) {
            String next = current.apply(tape);
            current = states.get(next);
        }
    }

    @Override
    public String toString() {
        return String.format("{States: %s, current state: %s, tape: %s}", states.keySet(), current.name(), tape);
    }
}
