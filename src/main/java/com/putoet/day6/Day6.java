package com.putoet.day6;

import com.putoet.resources.CSV;

import java.util.*;

public class Day6 {
    public static void main(String[] args) {
        final List<Integer> memoryBanks = CSV.flatList("/day6.txt", "\t", Integer::parseInt);

        final int after = reoccurrenceAfter(memoryBanks);
        System.out.println("Found reoccurring configuration after " + after + " redistributions.");

        final int count = reoccurrenceCount(memoryBanks);
        System.out.println("Found reoccurring configuration after " + count + " loops.");
    }

    public static int reoccurrenceAfter(List<Integer> memoryBanks) {
        final Set<List<Integer>> redistributed = new HashSet<>();

        int count = 0;
        List<Integer> next = memoryBanks;

        while (redistributed.add(next)){
            count++;
            next = redistribute(next);
        }
        return count;
    }

    public static int reoccurrenceCount(List<Integer> memoryBanks) {
        final Map<List<Integer>,Integer> redistributed = new HashMap<>();

        int count = 0;
        List<Integer> next = memoryBanks;

        while (!redistributed.containsKey(next)){
            redistributed.put(next, count);
            count++;
            next = redistribute(next);
        }

        return count - redistributed.get(next);
    }

    public static List<Integer> redistribute(List<Integer> memoryBanks) {
        final List<Integer> redistributed = new ArrayList<>(memoryBanks);
        final int highest = highest(redistributed);

        int idx = highest;
        int redistribute = redistributed.get(highest);
        redistributed.set(highest, 0);

        while (redistribute > 0) {
            idx = (idx + 1) % redistributed.size();
            redistributed.set(idx, redistributed.get(idx) + 1);
            redistribute--;
        }

        return redistributed;
    }

    private static int highest(List<Integer> redistributed) {
        int highestIdx = 0;
        int highestValue = -1;

        for (int idx = 0; idx < redistributed.size(); idx++)
            if (redistributed.get(idx) > highestValue) {
                highestIdx = idx;
                highestValue = redistributed.get(idx);
            }

        return highestIdx;
    }
}
