package com.putoet.day14;

import com.putoet.utils.Timer;

public class Day14 {
    public static void main(String[] args) {
        final var grid = DiskGrid.of("oundnydw");

        Timer.run(() -> System.out.println("Number of used blocks is " + grid.usedBlocks()));
        Timer.run(() -> System.out.println("Number os regions is " + grid.regions()));
    }
}
