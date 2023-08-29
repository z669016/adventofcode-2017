package com.putoet.day3;

import com.putoet.utils.Timer;

import java.util.ArrayList;
import java.util.List;

public class Day3 {
    public static void main(String[] args) {
        final var input = 277678;

        Timer.run(() -> {
            final var inputPoint = Sphere.point(input);
            System.out.println("Point for number " + input + " is " + inputPoint);
            System.out.println("Manhattan distance for number " + input + " is " + inputPoint.manhattanDistance());
        });

        Timer.run(() -> {
            final var list = new ArrayList<>(List.of(1));
            var value = 1;
            var idx = 2;
            while (value < input) {
                final int number = idx;
                final var numberPoint = Sphere.point(idx);
                final var adjacent = numberPoint.adjacent().stream()
                        .filter(point -> Sphere.number(point) < number)
                        .toList();

                value = adjacent.stream().mapToInt(point -> list.get(Sphere.number(point) - 1)).sum();
                list.add(value);
                idx++;
            }
            System.out.println("First value larger than " + input + " is " + value);
        });
    }
}
