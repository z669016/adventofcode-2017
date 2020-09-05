package com.putoet.day14;

public class Day14 {
    public static void main(String[] args) {
        final String inout = "oundnydw";
        final DiskGrid grid = DiskGrid.of(inout);

        System.out.println("Number of used blocks is " + grid.usedBlocks());
        System.out.println("Number os regions is " + grid.regions());
    }
}
