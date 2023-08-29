package com.putoet.day5;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day5 {
    public static void main(String[] args) {
        final var offsets = ResourceLines.stream("/day5.txt").mapToInt(Integer::parseInt).toArray();

        Timer.run(() -> {
            final var jumper = new Jumper(offsets);
            System.out.println("Number of steps to exit the list (increasing) is " + jumper.run());
        });

        Timer.run(() -> {
            final var jumper = new Jumper(offsets, true);
            System.out.println("Number of steps to exit the list (decreasing) is " + jumper.run());
        });
    }
}
