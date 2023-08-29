package com.putoet.day3;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

class Sphere {
    public static int numbersOnRing(int ring) {
        assert ring >= 0;

        return maxOnRing(ring) - minOnRing(ring) + 1;
    }

    public static int minOnRing(int ring) {
        assert ring >= 0;

        return ring == 0 ? 1 : 2 + factor(ring) * 8;
    }

    public static int maxOnRing(int ring) {
        assert ring >= 0;

        return ring == 0 ? 1 : minOnRing(ring + 1) - 1;
    }

    private static int factor(int ring) {
        assert ring >= 0;

        return IntStream.range(0, ring).sum();
    }

    public static int ring(int number) {
        assert number > 0;

        var ring = 0;
        while (number > maxOnRing(ring))
            ring++;

        return ring;
    }

    public static Point point(int number) {
        assert number > 0;

        if (number == 1)
            return Point.ORIGIN;

        final var ring = ring(number);
        final var offset = number - minOnRing(ring);
        final var numbersOnRing = numbersOnRing(ring);
        final var size = numbersOnRing / 4;

        final int block = block(offset, size);

        return switch(block) {
            case 0 -> Point.of(ring, -(ring - 1) + offset % size);
            case 1 -> Point.of((ring - 1) - offset % size, ring);
            case 2 -> Point.of(-ring, (ring - 1) - offset % size);
            case 3 -> Point.of(-(ring - 1) + offset % size, -ring);
            default -> throw new IllegalStateException("Unexpected block value: " + block);
        };
    }

    private static int block(int offset, int size) {
        var block = 0;
        while (offset > ((block + 1) * size - 1))
            block++;

        return block;
    }

    private static int block(Point point, int ring) {
        if (point.y() == -ring) return 3;
        if (point.x() == ring) return 0;
        if (point.y() == ring) return 1;
        if (point.x() == -ring) return 2;
        throw new IllegalStateException("Cannot determine block for point " + point + " on ring " + ring);
    }

    public static int number(@NotNull Point point) {
        if (point.equals(Point.ORIGIN))
            return 1;

        final var ring = ring(point);
        final var numbersOnRing = numbersOnRing(ring);
        final var size = numbersOnRing / 4;
        final var block = block(point, ring);

        final var offset = block * size + switch (block) {
            case 0 -> point.y() + ring - 1;
            case 1 -> Math.abs(ring - point.x() - 1);
            case 2 -> Math.abs(ring - point.y() - 1);
            case 3 -> ring + point.x() - 1;
            default -> throw new IllegalStateException("Unexpected block value: " + block);
        };

        return minOnRing(ring) + offset;
    }

    private static int ring(Point point) {
        return Math.max(Math.abs(point.x()), Math.abs(point.y()));
    }
}
