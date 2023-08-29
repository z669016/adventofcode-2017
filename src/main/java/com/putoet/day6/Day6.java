package com.putoet.day6;

import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.*;

public class Day6 {
    public static void main(String[] args) {
        final var memoryBanks = CSV.flatList("/day6.txt", "\t", Integer::parseInt);

        Timer.run(() ->
                System.out.println("Found reoccurring configuration after " + reoccurrenceAfter(memoryBanks) + " redistributions.")
        );

        Timer.run(() ->
                System.out.println("Found reoccurring configuration after " + reoccurrenceCount(memoryBanks) + " loops.")
        );
    }

    public static int reoccurrenceAfter(List<Integer> memoryBanks) {
        final var redistributed = new HashSet<List<Integer>>();

        var count = 0;
        var next = memoryBanks;

        while (redistributed.add(next)) {
            count++;
            next = redistribute(next);
        }
        return count;
    }

    public static int reoccurrenceCount(List<Integer> memoryBanks) {
        final var redistributed = new HashMap<List<Integer>, Integer>();

        var count = 0;
        var next = memoryBanks;

        while (!redistributed.containsKey(next)) {
            redistributed.put(next, count);
            count++;
            next = redistribute(next);
        }

        return count - redistributed.get(next);
    }

    public static List<Integer> redistribute(List<Integer> memoryBanks) {
        final var redistributed = new ArrayList<>(memoryBanks);
        final var highest = highest(redistributed);

        var idx = highest;
        var redistribute = redistributed.get(highest);
        redistributed.set(highest, 0);

        while (redistribute > 0) {
            idx = (idx + 1) % redistributed.size();
            redistributed.set(idx, redistributed.get(idx) + 1);
            redistribute--;
        }

        return redistributed;
    }

    private static int highest(List<Integer> redistributed) {
        var highestIdx = 0;
        var highestValue = -1;

        for (var idx = 0; idx < redistributed.size(); idx++)
            if (redistributed.get(idx) > highestValue) {
                highestIdx = idx;
                highestValue = redistributed.get(idx);
            }

        return highestIdx;
    }
}
