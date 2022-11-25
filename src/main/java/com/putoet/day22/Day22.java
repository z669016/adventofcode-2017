package com.putoet.day22;

import com.putoet.grid.Point;
import com.putoet.resources.ResourceLines;
import com.putoet.grid.GridUtils;

public class Day22 {
    public static void main(String[] args) {
        char[][] grid = GridUtils.of(ResourceLines.list("/day22.txt"));
        Virus virus = new Virus(Point.of(grid.length/2, grid.length/2));
        part(grid, virus, 10_000);

        grid = GridUtils.of(ResourceLines.list("/day22.txt"));
        virus = new SmarterVirus(Point.of(grid.length/2, grid.length/2));
        part(grid, virus, 10_000_000);
    }

    private static void part(char[][] grid, Virus virus, int count) {
        for (int i = 0; i < count; i++) {
            grid = virus.burst(grid);
        }
        System.out.println("# infections by busts is " + virus.burstInfectedCount());
    }
}
