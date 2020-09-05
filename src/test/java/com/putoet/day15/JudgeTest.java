package com.putoet.day15;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class JudgeTest {
    private Generator a;
    private Generator b;
    private Judge judge;

    @BeforeEach
    void setup() {
        a = new Generator("A", 16807, 65);
        b = new Generator("B", 48271, 8921);
        judge = new Judge();
    }

    @Test
    void compare() {
        IntStream.range(0, 5).forEach(i -> {
            judge.compare(a.get(), b.get());
        });

        assertEquals(1, judge.count());
    }

    @Test
    void count() {
        IntStream.range(0, 40_000_000).forEach(i -> {
            judge.compare(a.get(), b.get());
        });

        assertEquals(588, judge.count());
    }
}