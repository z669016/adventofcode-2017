package com.putoet.day19;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

class SeriesOfTubes {
    enum Direction {
        DOWN,
        LEFT,
        UP,
        RIGHT
    }

    private static final char VERTICAL_LINE = '|';
    private static final char HORIZONTAL_LINE = '-';
    private static final char CORNER = '+';
    private static final char BLANK = ' ';

    private final char[][] grid;
    private final List<Tube> tubes;

    private SeriesOfTubes(char[][] grid, List<Tube> tubes) {
        this.grid = grid;
        this.tubes = tubes;
    }

    public String letters() {
        return tubes.stream()
                .flatMap(tube -> tube.letters().stream())
                .collect(Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString));
    }

    public long steps() {
        return tubes.stream().mapToLong(Tube::steps).sum() + 1;
    }

    @Override
    public String toString() {
        final var sb = new StringBuffer();
        IntStream.range(0, grid.length).forEach(y -> sb.append(grid[y]).append("\n"));
        return sb.toString();
    }

    public static SeriesOfTubes of(@NotNull List<String> lines) {
        final var grid = grid(lines);
        final var tubes = tubes(grid);

        return new SeriesOfTubes(grid, tubes);
    }

    private static char[][] grid(List<String> lines) {
        final var maxLen = lines.stream().mapToInt(String::length).max().orElseThrow();
        final var grid = new char[lines.size()][maxLen];
        IntStream.range(0, lines.size())
                .forEach(y -> {
                    Arrays.fill(grid[y], ' ');
                    IntStream.range(0, lines.get(y).length()).forEach(x -> grid[y][x] = lines.get(y).charAt(x));
                });
        return grid;
    }

    private static List<Tube> tubes(char[][] grid) {
        final List<Tube> list = new ArrayList<>();
        final Point start = start(grid);

        var tube = createTube(grid, start);
        while (!Character.isLetter(grid[tube.end().y()][tube.end().x()])) {
            list.add(tube);
            tube = createTube(grid, tube);
        }
        list.add(tube);

        return list;
    }

    private static Tube createTube(char[][] grid, Point start) {
        return createTube(grid, start, direction(grid, start));
    }

    private static Tube createTube(char[][] grid, Tube tube) {
        return createTube(grid, tube.end(), direction(grid, tube));
    }

    private static Tube createTube(char[][] grid, Point start, Direction direction) {
        var y = start.y();
        var x = start.x();

        final var letters = new ArrayList<Character>();
        switch (direction) {
            case DOWN -> {
                do {
                    y++;
                    if (Character.isLetter(grid[y][x]))
                        letters.add(grid[y][x]);

                    if (grid[y][x] == BLANK) {
                        y--;
                        break;
                    }
                } while (grid[y][x] != CORNER);
            }
            case UP -> {
                do {
                    y--;
                    if (Character.isLetter(grid[y][x]))
                        letters.add(grid[y][x]);

                    if (grid[y][x] == BLANK) {
                        y++;
                        break;
                    }
                } while (grid[y][x] != CORNER && y > 0);
            }
            case LEFT -> {
                do {
                    x--;
                    if (Character.isLetter(grid[y][x]))
                        letters.add(grid[y][x]);

                    if (grid[y][x] == BLANK) {
                        x++;
                        break;
                    }
                } while (grid[y][x] != CORNER && x > 0);
            }
            case RIGHT -> {
                do {
                    x++;
                    if (Character.isLetter(grid[y][x]))
                        letters.add(grid[y][x]);

                    if (grid[y][x] == BLANK) {
                        x--;
                        break;
                    }
                } while (grid[y][x] != CORNER && x < grid[y].length);
            }
        }

        if (grid[y][x] == CORNER || Character.isLetter(grid[y][x]))
            return new Tube(start, Point.of(x, y), letters);

        throw new IllegalStateException("No end point found for line starting at " + start + " and moving " + direction);
    }

    private static Direction direction(char[][] grid, Point point) {
        final var y = point.y();
        final var x = point.x();

        if (y < grid.length - 1 && verticalSymbol(grid[y + 1][x])) return Direction.DOWN;
        if (y > 0 && verticalSymbol(grid[y - 1][x])) return Direction.UP;
        if (x < grid[y].length - 1 && horizontalSymbol(grid[y][x + 1])) return Direction.RIGHT;
        if (x > 0 && horizontalSymbol(grid[y][x - 1])) return Direction.LEFT;

        throw new IllegalStateException("Could not determine direction from point " + point);
    }

    private static boolean verticalSymbol(char c) {
        return Character.isLetter(c) || c == VERTICAL_LINE;
    }

    private static boolean horizontalSymbol(char c) {
        return Character.isLetter(c) || c == HORIZONTAL_LINE;
    }

    private static Direction direction(char[][] grid, Tube tube) {
        final var y = tube.end().y();
        final var x = tube.end().x();

        if (tube.start().y() != tube.end().y()) {
            if (x < grid[y].length - 1 && horizontalSymbol(grid[y][x + 1])) return Direction.RIGHT;
            if (x > 0 && horizontalSymbol(grid[y][x - 1])) return Direction.LEFT;
        } else {
            if (y < grid.length - 1 && verticalSymbol(grid[y + 1][x])) return Direction.DOWN;
            if (y > 0 && verticalSymbol(grid[y - 1][x])) return Direction.UP;
        }
        throw new IllegalStateException("Could not determine direction from previous tube " + tube);
    }

    private static Point start(char[][] grid) {
        for (var x = 0; x < grid[0].length; x++)
            if (grid[0][x] == VERTICAL_LINE) return Point.of(x, 0);

        throw new IllegalArgumentException("No stating point found on grid line 0 ('" + String.valueOf(grid[0]) + "')");
    }
}
