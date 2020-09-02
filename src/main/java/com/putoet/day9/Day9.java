package com.putoet.day9;

import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day9 {
    public static void main(String[] args) {
        final List<String> list = ResourceLines.list("/day9.txt");
        final GroupData data = new GroupData(list.get(0));
        final GroupDataStats stats = new GroupDataStats(data);

        stats.parse();

        System.out.println("Total score is " + stats.groupScore());

        final long garbageLength = stats.garbageLength();
        final long excludedCounter = stats.excludedCounter();
        System.out.println("Garbage length not included excluded characters is " + (garbageLength - 2 * excludedCounter));
    }
}
