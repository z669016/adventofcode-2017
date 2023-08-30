package com.putoet.day25;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

class Tape {
    private final List<BitSet> bits = new ArrayList<>();
    private int offset = 0;
    private int cursor = 0;

    public Tape() {
        bits.add(new BitSet());
    }

    public void right() {
        cursor += 1;

        if ((offset + cursor) >= bits.size() * 64) {
            bits.add(new BitSet());
        }
    }

    public void left() {
        cursor -= 1;

        if (offset + cursor < 0) {
            bits.add(0, new BitSet());
            offset += 64;
        }
    }

    public int blocks() {
        return bits.size();
    }

    public int read() {
        final var index = (offset + cursor) / 64;
        final var bitset = bits.get(index);

        return bitset.get(Math.abs(cursor) % 64) ? 1 : 0;
    }

    public void write(int value) {
        final var index = (offset + cursor) / 64;
        final var bitset = bits.get(index);

        bitset.set(Math.abs(cursor) % 64, value != 0);
    }

    public int bitsSet() {
        var count = 0;

        for (BitSet set : bits)
            for (int i = set.nextSetBit(0); i >= 0; i = set.nextSetBit(i+1))
                count++;

        return count;
    }

    @Override
    public String toString() {
        return String.format("{blocks: %d, offset: %d, cursor: %d, %s}",
                blocks(), offset, cursor, bits);
    }
}
