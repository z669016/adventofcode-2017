package com.putoet.day10;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KnotHash {
    private final int[] list;
    private int current = 0;
    private int skip = 0;

    public KnotHash() {
        list = new int[256];
        for (var idx = 0; idx < list.length; idx++)
            list[idx] = idx;
    }

    public KnotHash(int[] list) {
        this.list = Arrays.copyOf(list, list.length);
    }

    public void reverse(int size) {
        assert size > 0 && size <= list.length;

        if (size > 1)
            reverse(list, current, size);

        moveForward(size);
    }

    private static void reverse(int[] list, int current, int size) {
        final var reversed = new int[size];

        for (var idx = 0; idx < size; idx++)
            reversed[size - idx - 1] = list[(current + idx) % list.length];

        for (var idx = 0; idx < size; idx++)
            list[(current + idx) % list.length] = reversed[idx];
    }

    private void moveForward(int size) {
        current += size;
        current += skip;
        current = current % list.length;
        skip++;
    }

    public int checksum() {
        return list[0] * list[1];
    }

    public List<Integer> asList() {
        return Arrays.stream(list).boxed().collect(Collectors.toList());
    }

    private static List<Integer> sparseHash(List<Integer> input) {
        final var knotHash = new KnotHash();
        for (var i = 0; i < 64; i++) {
            input.forEach(knotHash::reverse);
        }

        return knotHash.asList();
    }

    private static List<Integer> denseHash(List<Integer> hash) {
        assert hash.size() % 16 == 0;

        final var denseHash = new ArrayList<Integer>();
        for (var block = 0; block < hash.size() / 16; block++) {
            denseHash.add(hash.get(block * 16) ^
                    hash.get(block * 16 + 1) ^
                    hash.get(block * 16 + 2) ^
                    hash.get(block * 16 + 3) ^
                    hash.get(block * 16 + 4) ^
                    hash.get(block * 16 + 5) ^
                    hash.get(block * 16 + 6) ^
                    hash.get(block * 16 + 7) ^
                    hash.get(block * 16 + 8) ^
                    hash.get(block * 16 + 9) ^
                    hash.get(block * 16 + 10) ^
                    hash.get(block * 16 + 11) ^
                    hash.get(block * 16 + 12) ^
                    hash.get(block * 16 + 13) ^
                    hash.get(block * 16 + 14) ^
                    hash.get(block * 16 + 15));
        }

        return denseHash;
    }

    private static String hexadecimal(List<Integer> list) {
        return list.stream().map(i -> String.format("%02x", i)).collect(Collectors.joining());
    }

    public static List<Integer> createKey(@NotNull String line) {
        final var list = new ArrayList<>(line.chars().boxed().toList());
        list.addAll(List.of(17, 31, 73, 47, 23));

        return list;
    }

    public static String hash(@NotNull List<Integer> input) {
        return hexadecimal(denseHash(sparseHash(input)));
    }
}
