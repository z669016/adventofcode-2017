package com.putoet.day5;

import java.util.Arrays;

class Jumper {
    private final int[] jumps;
    private final boolean decrease;

    public Jumper(int[] jumps) {
        this(jumps, false);
    }

    public Jumper(int[] jumps, boolean decrease) {
        assert jumps != null && jumps.length > 0;

        this.jumps = Arrays.copyOf(jumps, jumps.length);
        this.decrease = decrease;
    }

    public int run() {
        var ip = 0;
        var count = 0;

        while (ip >= 0 && ip < jumps.length) {
            final var current = ip;
            final var offset = jumps[current];

            ip += offset;
            jumps[current] = decrease ? decrease(offset) : increase(offset);
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
