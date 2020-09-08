package com.putoet.day19;

import com.putoet.resources.ResourceLines;

public class Day19 {
    public static void main(String[] args) {
        final SeriesOfTubes seriesOfTubes = SeriesOfTubes.of(ResourceLines.list("/day19.txt"));
        System.out.println("Letters in the series of tubes are " + seriesOfTubes.letters());
        System.out.println("The total number of steps is " + seriesOfTubes.steps());
    }
}
