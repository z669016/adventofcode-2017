package com.putoet.day3;

import com.putoet.grid.Point;

import java.util.ArrayList;
import java.util.List;

public class Day3 {
    public static void main(String[] args) {
        final int input = 277678;

        final Point inputPoint = Sphere.point(input);
        System.out.println("Point for number " + input + " is " + inputPoint);
        System.out.println("Manhattan distance for number " + input + " is " + inputPoint.manhattanDistance());

        final List<Integer> list = new ArrayList<>(List.of(1));
        int value = 1;
        int idx = 2;
        while (value < input) {
            final int number = idx;
            final Point numberPoint = Sphere.point(idx);
            final List<Point> adjacent = numberPoint.adjacent().stream()
                    .filter(point -> Sphere.number(point) < number)
                    .toList();

            value = adjacent.stream().mapToInt(point -> list.get(Sphere.number(point) - 1)).sum();
            list.add(value);
            idx++;
        }
        System.out.println("First value larger than " + input + " is " + value);
    }
}
