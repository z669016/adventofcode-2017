package com.putoet.day9;

import com.putoet.resources.ResourceLines;

public class Day9 {
    public static void main(String[] args) {
        final String input = ResourceLines.line("/day9.txt");
        final DataStats stats = DataStats.parse(input);

        System.out.println("Total score is " + stats.groupScore());

        final long garbageLength = stats.garbageLength();
        final long excludedCounter = stats.excludedCounter();
        System.out.println("Garbage length not included excluded characters is " + (garbageLength - 2 * excludedCounter));
    }
}
