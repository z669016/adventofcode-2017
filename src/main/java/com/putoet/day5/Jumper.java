package com.putoet.day5;

import java.util.ArrayList;
import java.util.List;

public class Jumper {
    private final List<Integer> jumps;
    private boolean decrease;

    public Jumper(List<Integer> jumps) {
        this(jumps, false);
    }

    public Jumper(List<Integer> jumps, boolean decrease) {
        assert jumps != null && jumps.size() > 0;

        this.jumps = new ArrayList<>(jumps);
        this.decrease = decrease;
    }

    public int run() {
        int ip = 0;
        int count = 0;

        while (ip >= 0 && ip < jumps.size()) {
            final int current = ip;
            final int offset = jumps.get(current);

            ip += offset;
            jumps.set(current, decrease ? decrease(offset) : increase(offset));
            count++;
        }

        return count;
    }

    private static int increase(int offset) {
        return offset + 1;
    }

    private static int decrease(int offset) {
        return offset >= 3 ? offset - 1 : increase(offset);
    }
}
