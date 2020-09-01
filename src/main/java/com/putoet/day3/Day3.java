package com.putoet.day3;

import utilities.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) {
        final int input = 277678;
        final Sphere sphere = new Sphere();

        final Point inputPoint = sphere.point(input);
        System.out.println("Point for number " + input + " is " + inputPoint);
        System.out.println("Manhattan distance for number " + input + " is " + Point.ORIGIN.manhattanDistance(inputPoint));

        final List<Integer> list = new ArrayList<>(List.of(1));
        int value = 1;
        int idx = 2;
        while (value < input) {
            final int number = idx;
            final Point numberPoint = sphere.point(idx);
            final List<Point> adjacent = numberPoint.adjacend().stream()
                    .filter(point -> sphere.number(point) < number)
                    .collect(Collectors.toList());

            value = adjacent.stream().mapToInt(point -> list.get(sphere.number(point) - 1)).sum();
            list.add(value);
            idx++;
        }
        System.out.println("First value larger than " + input + " is " + value);
    }
}
