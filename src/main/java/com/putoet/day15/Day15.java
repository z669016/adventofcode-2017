package com.putoet.day15;

import java.util.stream.IntStream;

public class Day15 {
    public static void main(String[] args) {
        Generator a = new Generator("A", 16807, 516);
        Generator b = new Generator("B", 48271, 190);
        judge(a, b, 40_000_000);

        a = new MultipleOfGenerator("A", 16807, 516, 4);
        b = new MultipleOfGenerator("B", 48271, 190, 8);
        judge(a, b, 5_000_000);
    }

    private static void judge(Generator a, Generator b, int max) {
        final Judge judge = new Judge();
        IntStream.range(0, max).forEach(i -> judge.compare(a.get(), b.get()));
        System.out.println("Judge count is " + judge.count());
    }
}
