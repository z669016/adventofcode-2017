package utilities;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GridUtils {
    public static char[][] of(List<String> lines) {
        return lines.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public static char[][] grow(char[][] grid, char init) {
        final int largerSize = grid.length * 3;
        final char[][] largerGrid = new char[largerSize][largerSize];

        for (int blockY = 0; blockY < 3; blockY++)
            for (int blockX = 0; blockX < 3; blockX++) {
                final int offsetY = blockY * grid.length;
                final int offsetX = blockX * grid.length;

                if (blockY == 1 && blockX == 1) {
                    for (int y = 0; y < grid.length; y++)
                        System.arraycopy(grid[y], 0, largerGrid[offsetY + y], offsetX, grid.length);
                } else {
                    for (int y = 0; y < grid.length; y++)
                        for (int x = 0; x < grid.length; x++)
                            Arrays.fill(largerGrid[offsetY + y], offsetX, offsetX + grid.length, init);
                            // largerGrid[offsetY + y][offsetX + x] = init;
                }
            }

        return largerGrid;
    }

    public static boolean gridEquals(char[][] a, char[][] b) {
        if (a.length != b.length)
            return false;

        for (int y = 0; y < a.length; y++) {
            if (a[y].length != b[y].length)
                return false;

            for (int x = 0; x < a[y].length; x++)
                if (a[y][x] != b[y][x])
                    return false;
        }

        return true;
    }

    public static long count(char[][] grid, char toCount) {
        return Arrays.stream(grid)
                .flatMapToInt(row -> CharBuffer.wrap(row).chars())
                .filter(c -> c == toCount)
                .count();
    }

    public static void print(char[][] grid) {
        Arrays.stream(grid)
                .sequential()
                .forEach(row -> System.out.println(String.valueOf(row)));
        System.out.println();
    }

    public static void print(char[][] grid, Point point, char marker) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (y == point.y && x == point.x)
                    System.out.print(marker);
                else
                    System.out.print(grid[y][x]);
            }
            System.out.println();
        }
    }

    public static String toString(char[][] grid) {
        return Arrays.stream(grid)
                .sequential()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public static List<String> toList(char[][] grid) {
        return Arrays.stream(grid)
                .sequential()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }
}
