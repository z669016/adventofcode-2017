package com.putoet.day14;

import utilities.KnotHash;
import utilities.Point;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DiskGrid {
    private final List<String> hexGrid;
    private final List<String> bitGrid;

    public static DiskGrid of(String key) {
        assert key != null;

        return new DiskGrid(IntStream.range(0, 128)
                .mapToObj(i -> KnotHash.createKey(key + "-" + i))
                .map(KnotHash::hash)
                .collect(Collectors.toList()));
    }

    public DiskGrid(List<String> hexGrid) {
        this.hexGrid = hexGrid;
        this.bitGrid = hexGrid.stream()
                .map(DiskGrid::decode)
                .collect(Collectors.toList());
    }

    public static String decode(String hash) {
        return hash.chars().mapToObj(DiskGrid::asBits).collect(Collectors.joining());
    }

    private static String asBits(int c) {
        final int digit = Character.digit(c, 16);
        return String.format("%4s", Integer.toBinaryString(digit)).replace(' ', '0');
    }

    @Override
    public String toString() {
        return String.join("\n", printable());
    }

    public long usedBlocks() {
        return bitGrid.stream().flatMapToInt(String::chars).filter(i -> i == '1').count();
    }

    public long regions() {
        final int[][] numbers = clusterRegions();
        return Arrays.stream(numbers)
                .flatMapToInt(Arrays::stream)
                .filter(i ->  i > 0)
                .distinct().count();
    }

    private int[][] clusterRegions() {
        final int[][] numbers = sequenceMatrix();

        int count = 1;
        Optional<Point> point = findFirst(numbers, count);
        while(point.isPresent()) {
            numbers[point.get().y][point.get().x] = count;
            clusterGroupFromPoint(numbers, point.get());

            point = findFirst(numbers, ++count);
        }
        return numbers;
    }

    private int[][] sequenceMatrix() {
        final int[][] numbers =  bitGrid.stream()
                .map(row -> row.chars().map(c -> c - '0').toArray()).toArray(int[][]::new);

        int count = 1;
        for (int y = 0; y < numbers.length; y++)
            for (int x = 0; x < numbers[y].length; x++)
                if (numbers[y][x] != 0) numbers[y][x] = count++;

        return numbers;
    }

    private static Optional<Point> findFirst(int numbers[][], int count) {
        for (int y = 0; y < numbers.length; y++)
            for (int x = 0; x < numbers[y].length; x++) {
                if (numbers[y][x] >= count) {
                    return Optional.of(Point.of(x, y));
                }
            }

        return Optional.empty();
    }

    private static void clusterGroupFromPoint(int[][] numbers, Point init) {
        final Queue<Point> queue = new LinkedList<>();
        final int count = numbers[init.y][init.x];
        queue.offer(init);

        while (!queue.isEmpty()) {
            final Point point = queue.poll();
            numbers[point.y][point.x] = count;

            if (point.x < numbers[point.y].length - 1)
                if (numbers[point.y][point.x+1] != 0 && numbers[point.y][point.x+1] != count) queue.offer(Point.of( point.x+1, point.y));
            if (point.x > 0)
                if (numbers[point.y][point.x-1] != 0 && numbers[point.y][point.x-1] != count) queue.offer(Point.of(point.x-1, point.y));
            if (point.y < numbers.length - 1)
                if (numbers[point.y+1][point.x] != 0 && numbers[point.y+1][point.x] != count) queue.offer(Point.of( point.x, point.y+1));
            if (point.y > 0)
                if (numbers[point.y-1][point.x] != 0 && numbers[point.y-1][point.x] != count) queue.offer(Point.of(point.x, point.y-1));
        }
    }

    public List<String> printable() {
        return  bitGrid.stream()
            .map(DiskGrid::printable)
            .collect(Collectors.toList());
    }

    private static  String printable(String line) {
        final StringBuffer sb = new StringBuffer();
        line.chars().forEach(c -> sb.append(c == '0' ? "." : '#'));
        return sb.toString();
    }
}
