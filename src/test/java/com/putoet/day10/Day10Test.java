package com.putoet.day10;

import org.junit.jupiter.api.Test;
import utilities.KnotHash;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {

    @Test
    void input() {
        final List<Integer> list = Day10.input("1,2,3");
        assertEquals(List.of(49,44,50,44,51,17,31,73,47,23), list);
    }
}