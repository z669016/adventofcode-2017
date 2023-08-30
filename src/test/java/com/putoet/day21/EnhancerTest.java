package com.putoet.day21;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;
import com.putoet.grid.GridUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EnhancerTest {
    public static final char[][] GRID2 = new char[][] { new char[] {'.', '.'}, new char[] {'.', '#'}};
    public static final char[][] GRID3 = new char[][] { new char[] {'.', '#', '.'}, new char[] {'.', '.', '#'}, new char[] {'#', '#', '#'}};
    public static final char[][] GRID4 = new char[][] { new char[] {'#', '.', '.', '#'}, new char[] {'.', '.', '.', '.'}, new char[] {'#', '.', '.', '#'}, new char[] {'.', '#', '#', '.'}};

    public static final String LINE2 = "../.#";
    public static final String LINE3 = ".#./..#/###";
    public static final String LINE4 = "#..#/..../#..#/.##.";

    @Test
    void grid2String() {
        assertEquals(LINE2, Enhancer.grid2String(GRID2));
        assertEquals(LINE3, Enhancer.grid2String(GRID3));
        assertEquals(LINE4, Enhancer.grid2String(GRID4));

        assertEquals("123/456/789", Enhancer.grid2String(new char[][]{"123".toCharArray(), "456".toCharArray(), "789".toCharArray()}));
    }

    @Test
    void string2grid() {
        assertTrue(GridUtils.gridEquals(GRID2, Enhancer.string2grid(LINE2)));
        assertTrue(GridUtils.gridEquals(GRID3, Enhancer.string2grid(LINE3)));
        assertTrue(GridUtils.gridEquals(GRID4, Enhancer.string2grid(LINE4)));

        assertTrue(GridUtils.gridEquals(new char[][]{"123".toCharArray(), "456".toCharArray(), "789".toCharArray()}, Enhancer.string2grid("123/456/789")));
    }

    @Test
    void transform() {
        final var enhancer = Enhancer.of(ResourceLines.list("/day21.txt"));

        var grid = enhancer.transform(GRID3);
        assertEquals("#..#/..../..../#..#", Enhancer.grid2String(grid));

        grid = enhancer.transform(grid);
        assertEquals("##.##./#..#../....../##.##./#..#../......", Enhancer.grid2String(grid));
        assertEquals(12, Enhancer.activePixels(grid));
    }

    @Test
    void rotations() {
        var grid = LINE3;
        System.out.println(grid);

        Arrays.stream(grid.split("/")).forEach(System.out::println);
        System.out.println();
        for (var r = 0; r < 3; r++) {
            grid = Enhancer.rotate(grid);
            System.out.println(grid);
            Arrays.stream(grid.split("/")).forEach(System.out::println);
            System.out.println();
        }
    }

    @Test
    void flips() {
        var grid = LINE3;
        System.out.println(grid);

        Arrays.stream(grid.split("/")).forEach(System.out::println);
        System.out.println();

        var flipped = Enhancer.horizontalFlip(grid);
        System.out.println(flipped);
        Arrays.stream(flipped.split("/")).forEach(System.out::println);
        System.out.println();

        flipped = Enhancer.verticalFlip(grid);
        System.out.println(flipped);
        Arrays.stream(flipped.split("/")).forEach(System.out::println);
        System.out.println();
    }
}