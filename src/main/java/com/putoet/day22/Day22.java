package com.putoet.day22;

import com.putoet.grid.Point;
import com.putoet.resources.ResourceLines;
import com.putoet.grid.GridUtils;
import com.putoet.utils.Timer;

public class Day22 {
    public static void main(String[] args) {
        Timer.run(() -> {
            final var grid = GridUtils.of(ResourceLines.list("/day22.txt"));
            var virus = new Virus(Point.of(grid.length / 2, grid.length / 2));
            part(grid, virus, 10_000);
        });

        Timer.run(() -> {
            final var grid = GridUtils.of(ResourceLines.list("/day22.txt"));
            final var virus = new SmarterVirus(Point.of(grid.length / 2, grid.length / 2));
            part(grid, virus, 10_000_000);
        });
    }

    private static void part(char[][] grid, Virus virus, int count) {
        for (var i = 0; i < count; i++) {
            grid = virus.burst(grid);
        }
        System.out.println("# infections by " + count + " bursts is " + virus.burstInfectedCount());
    }
}
