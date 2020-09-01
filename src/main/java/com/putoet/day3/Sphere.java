package com.putoet.day3;

import utilities.Point;

import java.util.stream.IntStream;

public class Sphere {
    public int numbersOnRing(int ring) {
        assert ring >= 0;

        return maxOnRing(ring) - minOnRing(ring) + 1;
    }

    public int minOnRing(int ring) {
        assert ring >= 0;

        return ring == 0 ? 1 : 2 + factor(ring) * 8;
    }

    public int maxOnRing(int ring) {
        assert ring >= 0;

        return ring == 0 ? 1 : minOnRing(ring + 1) - 1;
    }

    private int factor(int ring) {
        assert ring >= 0;

        return IntStream.range(0, ring).sum();
    }

    public int ring(int number) {
        assert number > 0;

        int ring = 0;
        while (number > maxOnRing(ring))
            ring++;

        return ring;
    }

    public Point point(int number) {
        assert number > 0;

        if (number == 1)
            return Point.ORIGIN;

        final int ring = ring(number);
        final int offset = number - minOnRing(ring);
        final int numbersOnRing = numbersOnRing(ring);
        final int size = numbersOnRing / 4;

        final int block = block(offset, size);

        return switch(block) {
            case 0 -> Point.of(ring, -(ring - 1) + offset % size);
            case 1 -> Point.of((ring - 1) - offset % size, ring);
            case 2 -> Point.of(-ring, (ring - 1) - offset % size);
            case 3 -> Point.of(-(ring - 1) + offset % size, -ring);
            default -> throw new IllegalStateException("Unexpected block value: " + block);
        };
    }

    private int block(int offset, int size) {
        int block = 0;
        while (offset > ((block + 1) * size - 1))
            block++;

        return block;
    }

    private int block(Point point, int ring) {
        int block = 0;
        if (point.y == -ring) return 3;
        if (point.x == ring) return 0;
        if (point.y == ring) return 1;
        if (point.x == -ring) return 2;
        throw new IllegalStateException("Cannot determine block for point " + point + " on ring " + ring);
    }

    public int number(Point point) {
        if (point.equals(Point.ORIGIN))
            return 1;

        final int ring = ring(point);
        final int numbersOnRing = numbersOnRing(ring);
        final int size = numbersOnRing / 4;
        final int block = block(point, ring);

        final int offset = block * size + switch (block) {
            case 0 -> point.y + ring - 1;
            case 1 -> Math.abs(ring - point.x - 1);
            case 2 -> Math.abs(ring - point.y - 1);
            case 3 -> ring + point.x - 1;
            default -> throw new IllegalStateException("Unexpected block value: " + block);
        };

        return minOnRing(ring) + offset;
    }

    private int ring(Point point) {
        return Math.max(Math.abs(point.x), Math.abs(point.y));
    }

    private Point ringOrigin(int ring) {
        return Point.of(ring, -(ring -1));
    }
}
