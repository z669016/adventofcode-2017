package com.putoet.day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Spreadsheet {
    private final List<List<Integer>> matrix;

    public Spreadsheet(List<List<Integer>> matrix) {
        this.matrix = matrix;
    }

    public static Spreadsheet of(List<String> lines) {
        return new Spreadsheet(lines.stream()
                .map(line -> line.split("\t"))
                .map(array -> Arrays.stream(array)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList())
        );
    }

    public boolean is(List<List<Integer>> matrix) {
        if (this.matrix.size() != matrix.size())
            return false;

        for (int idy = 0; idy < this.matrix.size(); idy++) {
            final List<Integer> thisRow = this.matrix.get(idy);
            final List<Integer> row = matrix.get(idy);

            if (thisRow.size() != row.size())
                return false;

            for (int idx = 0; idx < thisRow.size(); idx++)
                if (thisRow.get(idx) != row.get(idx))
                    return false;
        }

        return true;
    }

    public List<Integer> min() {
        if (matrix.size() == 0)
            return List.of();

        return matrix.stream()
                .map(row -> row.stream().mapToInt(Integer::intValue).min().getAsInt())
                .collect(Collectors.toList());
    }

    public List<Integer> max() {
        if (matrix.size() == 0)
            return List.of();

        return matrix.stream()
                .map(row -> row.stream().mapToInt(Integer::intValue).max().getAsInt())
                .collect(Collectors.toList());
    }

    public long checksum() {
        final List<Integer> min = min();
        final List<Integer> max = max();

        long checksum = 0;
        for (int idx = 0; idx < min.size(); idx++)
            checksum += (Math.abs(min.get(idx) - max.get(idx)));

        return checksum;
    }

    public List<List<Integer>> evenlyDivisableValues() {
        if (matrix.size() == 0)
            return List.of();

        return matrix.stream()
                .map(Spreadsheet::evenlyDivisableValues)
                .collect(Collectors.toList());
    }

    private static List<Integer> evenlyDivisableValues(List<Integer> row) {
        for (int idx = 0; idx < row.size() - 1; idx++) {
            final int x = row.get(idx);

            for (int idz = idx + 1; idz < row.size(); idz++) {
                final int z = row.get(idz);

                if (x % z == 0)
                    return List.of(x, z);
                if (z % x == 0)
                    return List.of(z, x);
            }
        }

        throw new IllegalStateException("No evenly numbers for row " + row);
    }
}
