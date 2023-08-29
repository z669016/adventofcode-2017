package com.putoet.day9;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day9 {
    public static void main(String[] args) {
        Timer.run(() -> {
            final var input = ResourceLines.line("/day9.txt");
            final var stats = DataStats.parse(input);

            System.out.println("Total score is " + stats.groupScore());

            final var garbageLength = stats.garbageLength();
            final var excludedCounter = stats.excludedCounter();
            System.out.println("Garbage length not included excluded characters is " + (garbageLength - 2 * excludedCounter));
        });
    }
}
