package com.putoet.day25;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Tape {
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

    public int cursor() { return cursor; }
    public int offset() { return offset; }

    public int read() {
        final int index = (offset + cursor) / 64;
        final BitSet bitset = bits.get(index);

        return bitset.get(Math.abs(cursor) % 64) ? 1 : 0;
    }

    public void write(int value) {
        final int index = (offset + cursor) / 64;
        final BitSet bitset = bits.get(index);

        bitset.set(Math.abs(cursor) % 64, value != 0);
    }

    public int bitsSet() {
        int count = 0;

        for (BitSet set : bits)
            for (int i = set.nextSetBit(0); i >= 0; i = set.nextSetBit(i+1))
                count++;

        return count;
    }

    @Override
    public String toString() {
        return String.format("{blocks: %d, offset: %d, cursor: %d, %s}",
                blocks(), offset, cursor, bits.toString());
    }
}
