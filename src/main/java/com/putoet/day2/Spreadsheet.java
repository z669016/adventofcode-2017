package com.putoet.day2;

import com.google.common.collect.Streams;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;

public record Spreadsheet(List<List<Integer>> matrix) {
    public Spreadsheet {
        assert matrix != null;
    }

    public static Spreadsheet of(List<String> lines) {
        assert lines != null;

        return new Spreadsheet(lines.stream()
                .map(line -> line.split("\t"))
                .map(array -> Arrays.stream(array)
                        .map(Integer::parseInt)
                        .toList())
                .toList()
        );
    }

    public List<Integer> min() {
        if (matrix.size() == 0)
            return List.of();

        return matrix.stream()
                .map(row -> row.stream().mapToInt(Integer::intValue).min().orElseThrow())
                .toList();
    }

    public List<Integer> max() {
        if (matrix.size() == 0)
            return List.of();

        return matrix.stream()
                .map(row -> row.stream().mapToInt(Integer::intValue).max().orElseThrow())
                .toList();
    }

    public long checksum() {
        final List<Integer> min = min();
        final List<Integer> max = max();

        //noinspection UnstableApiUsage
        return Streams.zip(min.stream(), max.stream(), Pair::with)
                .mapToInt(p -> Math.abs(p.getValue0() - p.getValue1()))
                .sum();
    }

    public List<Pair<Integer,Integer>> evenlyDividableValues() {
        if (matrix.size() == 0)
            return List.of();

        return matrix.stream()
                .map(Spreadsheet::evenlyDividableValues)
                .toList();
    }

    private static Pair<Integer,Integer> evenlyDividableValues(List<Integer> row) {
        for (int idx = 0; idx < row.size() - 1; idx++) {
            final int x = row.get(idx);

            for (int idz = idx + 1; idz < row.size(); idz++) {
                final int z = row.get(idz);

                if (x % z == 0)
                    return Pair.with(x, z);
                if (z % x == 0)
                    return Pair.with(z, x);
            }
        }

        throw new IllegalStateException("No evenly numbers for row " + row);
    }
}
