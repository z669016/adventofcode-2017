package com.putoet.day22;

import com.putoet.resources.ResourceLines;
import utilities.GridUtils;
import utilities.Point;

public class Day22 {
    public static void main(String[] args) {
        char[][] grid = GridUtils.of(ResourceLines.list("/day22.txt"));
        final Virus virus = new Virus(Point.of(grid.length/2, grid.length/2));

        for (int i = 0; i < 10_000; i++) {
            grid = virus.burst(grid);
        }

        System.out.println("# infections by busts is " + virus.burstInfectedCount());
    }
}
