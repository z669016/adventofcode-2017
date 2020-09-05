package com.putoet.day14;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiskGridTest {

    @Test
    void testToString() {
        final DiskGrid grid = DiskGrid.of("flqrgnkx");
        final List<String> printable = grid.printable();

        assertEquals(8108, grid.usedBlocks());
        assertTrue(printable.get(0).startsWith("##.#.#.."));
        assertTrue(printable.get(1).startsWith(".#.#.#.#"));
        assertTrue(printable.get(2).startsWith("....#.#."));
        assertTrue(printable.get(3).startsWith("#.#.##.#"));
        assertTrue(printable.get(4).startsWith(".##.#..."));
        assertTrue(printable.get(5).startsWith("##..#..#"));
        assertTrue(printable.get(6).startsWith(".#...#.."));
        assertTrue(printable.get(7).startsWith("##.#.##."));
    }

    @Test
    void regions() {
        final DiskGrid grid = DiskGrid.of("flqrgnkx");
        assertEquals(1242, grid.regions());
    }
}