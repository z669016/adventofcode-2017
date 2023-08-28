package com.putoet.day2;

import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

record Spreadsheet(@NotNull List<List<Integer>> matrix) {
    public static Spreadsheet of(@NotNull List<String> lines) {
        return new Spreadsheet(lines.stream()
                .map(line -> line.split("\t"))
                .map(array -> Arrays.stream(array)
                        .map(Integer::parseInt)
                        .toList())
                .toList()
        );
    }

    public List<Integer> min() {
        if (matrix.isEmpty())
            return List.of();

        return matrix.stream()
                .map(row -> row.stream().mapToInt(Integer::intValue).min().orElseThrow())
                .toList();
    }

    public List<Integer> max() {
        if (matrix.isEmpty())
            return List.of();

        return matrix.stream()
                .map(row -> row.stream().mapToInt(Integer::intValue).max().orElseThrow())
                .toList();
    }

    public long checksum() {
        final var min = min();
        final var max = max();

        if (min.size() != max.size())
            throw new IllegalStateException("min and max lists have unequal size");

        return IntStream.range(0, min.size())
                .map(i -> Math.abs(min.get(i) - max.get(i)))
                .sum();
    }

    public List<Pair<Integer,Integer>> evenlyDividableValues() {
        if (matrix.isEmpty())
            return List.of();

        return matrix.stream()
                .map(Spreadsheet::evenlyDividableValues)
                .toList();
    }

    private static Pair<Integer,Integer> evenlyDividableValues(List<Integer> row) {
        for (var idx = 0; idx < row.size() - 1; idx++) {
            final var x = row.get(idx);

            for (var idz = idx + 1; idz < row.size(); idz++) {
                final var z = row.get(idz);

                if (x % z == 0)
                    return Pair.with(x, z);
                if (z % x == 0)
                    return Pair.with(z, x);
            }
        }

        throw new IllegalStateException("No evenly numbers for row " + row);
    }
}
