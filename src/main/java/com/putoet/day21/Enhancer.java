package com.putoet.day21;

import com.putoet.grid.GridUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

class Enhancer {
    public static final char[][] INITIAL_GRID = string2grid(".#./..#/###");
    public static final String SEPARATOR = "/";

    private final Map<String, char[][]> rules = new HashMap<>();

    private void addRule(String from, String to) {
        rules.put(from, string2grid(to));
    }

    public char[][] transform(char[][] fromGrid) {
        assert fromGrid != null;

        final var fromSize = gridSize(fromGrid);
        final var blocks = fromGrid.length / fromSize;
        final var toSize = fromSize == 2 ? 3 : 4;
        final var toGrid = new char[blocks * toSize][blocks * toSize];

        for (var y = 0; y < blocks; y++)
            for (var x = 0; x < blocks; x++)
                transform(fromGrid, y * fromSize, x * fromSize, toGrid);

        return toGrid;
    }

    public void transform(char[][] fromGrid, int fromYOffset, int fromXOffset, char[][] toGrid) {
        assert fromGrid != null;
        assert toGrid != null;

        final var key = grid2String(fromGrid, fromYOffset, fromXOffset);
        final var transformation = selectRule(key);

        final var fromSize = gridSize(fromGrid);
        final var yOffset = fromYOffset / fromSize;
        final var xOffset = fromXOffset / fromSize;
        final var toSize = transformation.length;
        final var toYOffset = yOffset * toSize;
        final var toXOffset = xOffset * toSize;

        for (var y = 0; y < toSize; y++)
            System.arraycopy(transformation[y], 0, toGrid[y + toYOffset], toXOffset, toSize);
    }

    private char[][] selectRule(String grid) {
        final var rotation = selectRotationRule(grid);
        if (rotation != null)
            return rotation;

        final var flip = selectFlipRule(grid);
        if (flip != null)
            return flip;

        throw new IllegalArgumentException("Cannot find a transformation rule for this grid '" + grid + "'");
    }

    private char[][] selectRotationRule(String grid) {
        for (var rotation = 0; rotation < 4; rotation++) {
            grid = rotate(grid);
            if (rules.containsKey(grid))
                return rules.get(grid);
        }

        return null;
    }

    private char[][] selectFlipRule(String grid) {
        var flipped = horizontalFlip(grid);
        var rotatedFlipped = selectRotationRule(flipped);
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
        final var sb = new StringBuilder();

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
        final var sb = new StringBuilder();

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
        final var sb = new StringBuilder();

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
        assert grid != null;

        return Arrays.stream(grid)
                .sequential()
                .map(String::valueOf)
                .collect(Collectors.joining(SEPARATOR));
    }

    public static String grid2String(char[][] grid, int yOffset, int xOffset) {
        assert grid != null;

        final var size = gridSize(grid);
        final var sb = new StringBuilder();
        for (var y = 0; y < size; y++) {
            for (var x = 0; x < size; x++) {
                sb.append(grid[y + yOffset][x + xOffset]);
            }
            if (y < size - 1)
                sb.append(SEPARATOR);
        }

        return sb.toString();
    }

    public static char[][] string2grid(@NotNull String line) {
        return Arrays.stream(line.split(SEPARATOR))
                .sequential()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public static Enhancer of(@NotNull List<String> lines) {
        final var enhancer = new Enhancer();
        lines.forEach(line -> {
            final var parts = line.split(" => ");
            if (parts.length != 2)
                System.out.println("Invalid enhancement rule '" + line + "' will be ignored");
            else
                enhancer.addRule(parts[0], parts[1]);
        });

        return enhancer;
    }

    public static long activePixels(char[][] grid) {
        assert grid != null;

        return GridUtils.count(grid, '#');
    }
}
