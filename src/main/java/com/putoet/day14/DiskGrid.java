package com.putoet.day14;

import com.putoet.grid.Point;
import com.putoet.day10.KnotHash;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DiskGrid {
    private final List<String> bitGrid;

    public static DiskGrid of(@NotNull String key) {
        return new DiskGrid(IntStream.range(0, 128)
                .mapToObj(i -> KnotHash.createKey(key + "-" + i))
                .map(KnotHash::hash)
                .toList()
        );
    }

    public DiskGrid(@NotNull List<String> hexGrid) {
        this.bitGrid = hexGrid.stream()
                .map(DiskGrid::decode)
                .toList();
    }

    public static String decode(@NotNull String hash) {
        return hash.chars().mapToObj(DiskGrid::asBits).collect(Collectors.joining());
    }

    private static String asBits(int c) {
        final var digit = Character.digit(c, 16);
        return String.format("%4s", Integer.toBinaryString(digit)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return String.join("\n", printable());
    }

    public long usedBlocks() {
        return bitGrid.stream()
                .flatMapToInt(String::chars)
                .filter(i -> i == '1')
                .count();
    }

    public long regions() {
        final var numbers = clusterRegions();
        return Arrays.stream(numbers)
                .flatMapToInt(Arrays::stream)
                .filter(i -> i > 0)
                .distinct()
                .count();
    }

    private int[][] clusterRegions() {
        final var numbers = sequenceMatrix();

        var count = 1;
        var point = findFirst(numbers, count);
        while (point.isPresent()) {
            numbers[point.get().y()][point.get().x()] = count;
            clusterGroupFromPoint(numbers, point.get());

            point = findFirst(numbers, ++count);
        }
        return numbers;
    }

    private int[][] sequenceMatrix() {
        final var numbers = bitGrid.stream()
                .map(row -> row.chars().map(c -> c - '0').toArray())
                .toArray(int[][]::new);

        var count = 1;
        for (var y = 0; y < numbers.length; y++)
            for (var x = 0; x < numbers[y].length; x++)
                if (numbers[y][x] != 0) numbers[y][x] = count++;

        return numbers;
    }

    private static Optional<Point> findFirst(int[][] numbers, int count) {
        for (var y = 0; y < numbers.length; y++)
            for (var x = 0; x < numbers[y].length; x++) {
                if (numbers[y][x] >= count) {
                    return Optional.of(Point.of(x, y));
                }
            }

        return Optional.empty();
    }

    private static void clusterGroupFromPoint(int[][] numbers, Point init) {
        final var queue = new LinkedList<Point>();
        final var count = numbers[init.y()][init.x()];
        queue.offer(init);

        while (!queue.isEmpty()) {
            final var point = queue.poll();
            numbers[point.y()][point.x()] = count;

            if (point.x() < numbers[point.y()].length - 1)
                if (numbers[point.y()][point.x() + 1] != 0 && numbers[point.y()][point.x() + 1] != count)
                    queue.offer(Point.of(point.x() + 1, point.y()));

            if (point.x() > 0)
                if (numbers[point.y()][point.x() - 1] != 0 && numbers[point.y()][point.x() - 1] != count)
                    queue.offer(Point.of(point.x() - 1, point.y()));

            if (point.y() < numbers.length - 1)
                if (numbers[point.y() + 1][point.x()] != 0 && numbers[point.y() + 1][point.x()] != count)
                    queue.offer(Point.of(point.x(), point.y() + 1));

            if (point.y() > 0)
                if (numbers[point.y() - 1][point.x()] != 0 && numbers[point.y() - 1][point.x()] != count)
                    queue.offer(Point.of(point.x(), point.y() - 1));
        }
    }

    public List<String> printable() {
        return bitGrid.stream()
                .map(DiskGrid::printable)
                .toList();
    }

    private static String printable(String line) {
        return line.chars().mapToObj(c -> c == '0' ? "." : "#").collect(Collectors.joining());
    }
}
