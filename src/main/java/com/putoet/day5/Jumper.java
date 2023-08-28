package com.putoet.day5;

public class Jumper {
    private final int[] jumps;
    private final boolean decrease;

    public Jumper(int[] jumps) {
        this(jumps, false);
    }

    public Jumper(int[] jumps, boolean decrease) {
        assert jumps != null && jumps.length > 0;

        this.jumps = jumps;
        this.decrease = decrease;
    }

    public int run() {
        int ip = 0;
        int count = 0;

        while (ip >= 0 && ip < jumps.length) {
            final int current = ip;
            final int offset = jumps[current];

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
