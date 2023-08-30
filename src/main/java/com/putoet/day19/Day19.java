package com.putoet.day19;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day19 {
    public static void main(String[] args) {
        final var seriesOfTubes = SeriesOfTubes.of(ResourceLines.list("/day19.txt"));
        Timer.run(() -> System.out.println("Letters in the series of tubes are " + seriesOfTubes.letters()));
        Timer.run(() -> System.out.println("The total number of steps is " + seriesOfTubes.steps()));
    }
}
