package com.putoet.day21;

import com.putoet.resources.ResourceLines;

public class Day21 {
    public static void main(String[] args) {
        final Enhancer enhancer = Enhancer.of(ResourceLines.list("/day21.txt"));
        transform(enhancer, 5);
        transform(enhancer, 18);
    }

    private static void transform(Enhancer enhancer, int count) {
        char[][] grid = Enhancer.INITIAL_GRID;

        for (int i = 0; i < count; i++) {
            System.out.print('.');
            grid = enhancer.transform(grid);
        }
        System.out.println();
        System.out.println("Active pixels in " + count + "-times-transformed grid is " + Enhancer.activePixels(grid));
    }
}
