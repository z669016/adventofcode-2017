package com.putoet.day10;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {

    @Test
    void input() {
        final List<Integer> list = Day10.input("1,2,3");
        assertEquals(List.of(49,44,50,44,51,17,31,73,47,23), list);
    }

    @Test
    void denseHash() {
        final List<Integer> list = List.of(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22);
        final List<Integer> denseHash = Day10.denseHash(list);
        assertEquals(1, denseHash.size());
        assertEquals(64, denseHash.get(0));
    }

    @Test
    void hexadecimal() {
        assertEquals("a2582a3a0e66e6e86e3812dcb672a272", getHexadecimal(""));
        assertEquals("33efeb34ea91902bb2f59c9920caa6cd", getHexadecimal("AoC 2017"));
        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", getHexadecimal("1,2,3"));
        assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", getHexadecimal("1,2,4"));
    }

    private String getHexadecimal(String line) {
        return Day10.hexadecimal(Day10.denseHash(Day10.sparseHash(Day10.input(line))));
    }
}