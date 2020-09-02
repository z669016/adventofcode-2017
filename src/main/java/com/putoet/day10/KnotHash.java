package com.putoet.day10;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KnotHash {
    private int[] list;
    private int current = 0;
    private int skip = 0;

    public KnotHash() {
        list = new int[256];
        for (int idx = 0; idx < list.length; idx++)
            list[idx] = idx;
    }

    public KnotHash(int[] list) {
        this.list = new int[list.length];
        for (int idx = 0; idx < list.length; idx++)
            this.list[idx] = list[idx];
    }

    public void reverse(int size) {
        assert size > 0 && size <= list.length;

        if (size > 1)
            reverse(list, current, size);

        moveForward(size);
    }

    public int checksum() {
        return list[0] * list[1];
    }

    private void moveForward(int size) {
        current += size;
        current += skip;
        current = current % list.length;
        skip++;
    }

    public List<Integer> asList() {
        return Arrays.stream(list).boxed().collect(Collectors.toList());
    }

    private static void reverse(int[] list, int current, int size) {
        final int[] reversed = new int[size];

        for (int idx = 0; idx < size; idx++)
            reversed[size - idx - 1] = list[(current + idx) % list.length];

        for (int idx = 0; idx < size; idx++)
            list[(current + idx) % list.length] = reversed[idx];
    }
}
