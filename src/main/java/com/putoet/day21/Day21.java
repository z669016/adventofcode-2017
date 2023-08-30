package com.putoet.day21;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day21 {
    public static void main(String[] args) {
        final var enhancer = Enhancer.of(ResourceLines.list("/day21.txt"));
        Timer.run(() -> transform(enhancer, 5));
        Timer.run(() -> transform(enhancer, 18));
    }

    private static void transform(Enhancer enhancer, int count) {
        var grid = Enhancer.INITIAL_GRID;
        for (var i = 0; i < count; i++) {
            grid = enhancer.transform(grid);
        }

        System.out.println("Active pixels in " + count + "-times-transformed grid is " + Enhancer.activePixels(grid));
    }
}
