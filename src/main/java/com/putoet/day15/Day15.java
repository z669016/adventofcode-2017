package com.putoet.day15;

import com.putoet.utils.Timer;

import java.util.stream.IntStream;

public class Day15 {
    public static void main(String[] args) {
        Timer.run(() -> {
            final var a = new Generator(16807, 516);
            final var b = new Generator(48271, 190);
            judge(a, b, 40_000_000);
        });

        Timer.run(() -> {
            final var a = new MultipleOfGenerator(16807, 516, 4);
            final var b = new MultipleOfGenerator(48271, 190, 8);
            judge(a, b, 5_000_000);
        });
    }

    private static void judge(Generator a, Generator b, int max) {
        final var judge = new Judge();
        IntStream.range(0, max).forEach(i -> judge.compare(a.get(), b.get()));
        System.out.println("Judge count is " + judge.count());
    }
}
