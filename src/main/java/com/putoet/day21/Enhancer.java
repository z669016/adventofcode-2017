package com.putoet.day21;

import com.putoet.grid.GridUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Enhancer {
    public static final char[][] INITIAL_GRID = string2grid(".#./..#/###");
    public static final String SEPARATOR = "/";

    private final Map<String, char[][]> rules = new HashMap<>();

    private void addRule(String from, String to) {
        rules.put(from, string2grid(to));
    }

    public char[][] transform(char[][] fromGrid) {
        final int fromSize = gridSize(fromGrid);
        final int blocks = fromGrid.length / fromSize;
        final int toSize = fromSize == 2 ? 3 : 4;
        final char[][] toGrid = new char[blocks * toSize][blocks * toSize];

        for (int y = 0; y < blocks; y++)
            for (int x = 0; x < blocks; x++)
                transform(fromGrid, y * fromSize, x * fromSize, toGrid);

        return toGrid;
    }

    public void transform(char[][] fromGrid, int fromYOffset, int fromXOffset, char[][] toGrid) {
        final String key = grid2String(fromGrid, fromYOffset, fromXOffset);
        final char[][] transformation = selectRule(key);

        final int fromSize = gridSize(fromGrid);
        final int yOffset = fromYOffset / fromSize;
        final int xOffset = fromXOffset / fromSize;
        final int toSize = transformation.length;
        final int toYOffset = yOffset * toSize;
        final int toXOffset = xOffset * toSize;

        for (int y = 0; y < toSize; y++)
            System.arraycopy(transformation[y], 0, toGrid[y + toYOffset], toXOffset, toSize);
    }

    private char[][] selectRule(String grid) {
        final char[][] rotation = selectRotationRule(grid);
        if (rotation != null)
            return rotation;

        final char[][] flip = selectFlipRule(grid);
        if (flip != null)
            return flip;

        throw new IllegalArgumentException("Cannot find a transformation rule for this grid '" + grid + "'");
    }

    private char[][] selectRotationRule(String grid) {
        for (int rotation = 0; rotation < 4; rotation++) {
            grid = rotate(grid);
            if (rules.containsKey(grid))
                return rules.get(grid);
        }

        return null;
    }

    private char[][] selectFlipRule(String grid) {
        String flipped = horizontalFlip(grid);
        char[][] rotatedFlipped = selectRotationRule(flipped);
        if (rotatedFlipped != null)
            return rotatedFlipped;

        flipped = verticalFlip(grid);
        rotatedFlipped = selectRotationRule(flipped);
        if (rotatedFlipped != null)
            return rotatedFlipped;

        flipped = horizontalFlip(verticalFlip(grid));
        rotatedFlipped = selectRotationRule(flipped);
        if (rotatedFlipped != null)
            return rotatedFlipped;

        flipped = verticalFlip(horizontalFlip(grid));
        rotatedFlipped = selectRotationRule(flipped);
        return rotatedFlipped;
    }

    public static String horizontalFlip(String grid) {
        final StringBuilder sb = new StringBuilder();

        // 0 1 /   --> 1 0 /
        // 3 4         4 3
        //
        // 0  1  2 /    -->  2  1  0 /
        // 4  5  6 /         6  5  4
        // 8  9 10          10  9  8

        switch (grid.length()) {
            case 5 -> sb.append(grid.charAt(1))
                    .append(grid.charAt(0))
                    .append(SEPARATOR)
                    .append(grid.charAt(4))
                    .append(grid.charAt(3));
            case 11 -> sb.append(grid.charAt(2))
                    .append(grid.charAt(1))
                    .append(grid.charAt(0))
                    .append(SEPARATOR)
                    .append(grid.charAt(6))
                    .append(grid.charAt(5))
                    .append(grid.charAt(4))
                    .append(SEPARATOR)
                    .append(grid.charAt(10))
                    .append(grid.charAt(9))
                    .append(grid.charAt(8));
            default ->
                    throw new IllegalArgumentException("Cannot horizontally flip this grid '" + grid +"'");

        }
        return sb.toString();
    }

    public static String verticalFlip(String grid) {
        final StringBuilder sb = new StringBuilder();

        // 0 1 /   --> 3 4 /
        // 3 4         0 1
        //
        // 0  1  2 /    -->  8  9 10 /
        // 4  5  6 /         4  5  6 /
        // 8  9 10           0  1  2

        switch (grid.length()) {
            case 5 -> sb.append(grid.charAt(3))
                    .append(grid.charAt(4))
                    .append(SEPARATOR)
                    .append(grid.charAt(0))
                    .append(grid.charAt(1));
            case 11 -> sb.append(grid.charAt(8))
                    .append(grid.charAt(9))
                    .append(grid.charAt(10))
                    .append(SEPARATOR)
                    .append(grid.charAt(4))
                    .append(grid.charAt(5))
                    .append(grid.charAt(6))
                    .append(SEPARATOR)
                    .append(grid.charAt(0))
                    .append(grid.charAt(1))
                    .append(grid.charAt(2));
            default ->
                    throw new IllegalArgumentException("Cannot vertically flip this grid '" + grid +"'");

        }
        return sb.toString();
    }

    public static String rotate(String grid) {
        final StringBuilder sb = new StringBuilder();

        // 0 1 /   --> 3 0 /
        // 3 4         4 1
        //
        // 0  1  2 /    -->  8  4  0 /
        // 4  5  6 /         9  5  1 /
        // 8  9 10          10  6  2

        switch (grid.length()) {
            case 5 -> sb.append(grid.charAt(3))
                    .append(grid.charAt(0))
                    .append(SEPARATOR)
                    .append(grid.charAt(4))
                    .append(grid.charAt(1));
            case 11 -> sb.append(grid.charAt(8))
                    .append(grid.charAt(4))
                    .append(grid.charAt(0))
                    .append(SEPARATOR)
                    .append(grid.charAt(9))
                    .append(grid.charAt(5))
                    .append(grid.charAt(1))
                    .append(SEPARATOR)
                    .append(grid.charAt(10))
                    .append(grid.charAt(6))
                    .append(grid.charAt(2));
            default ->
                    throw new IllegalArgumentException("Cannot rotate this grid '" + grid +"'");
        }

        return sb.toString();
    }

    private static int gridSize(char[][] from) {
        return from.length % 2 == 0 ? 2 : 3;
    }

    public static String grid2String(char[][] grid) {
        return Arrays.stream(grid)
                .sequential()
                .map(String::valueOf)
                .collect(Collectors.joining(SEPARATOR));
    }

    public static String grid2String(char[][] grid, int yOffset, int xOffset) {
        final int size = gridSize(grid);
        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                sb.append(grid[y + yOffset][x + xOffset]);
            }
            if (y < size - 1)
                sb.append(SEPARATOR);
        }

        return sb.toString();
    }

    public static char[][] string2grid(String line) {
        return Arrays.stream(line.split(SEPARATOR))
                .sequential()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public static Enhancer of(List<String> lines) {
        assert lines != null;

        final Enhancer enhancer = new Enhancer();
        lines.forEach(line -> {
            final String[] parts = line.split(" => ");
            if (parts.length != 2)
                System.out.println("Invalid enhancement rule '" + line + "' will be ignored");
            else
                enhancer.addRule(parts[0], parts[1]);
        });

        return enhancer;
    }

    public static long activePixels(char[][] grid) {
        return GridUtils.count(grid, '#');
    }
}
