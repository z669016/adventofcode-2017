package com.putoet.day17;

import com.putoet.utils.Timer;

import java.util.stream.IntStream;

public class Day17 {
    public static void main(String[] args) {
        final int input = 369;

        Timer.run(() -> {
            final var spinLock = new SpinLockImpl(input);
            part(spinLock, 2017);
        });

        Timer.run(() -> {
            final var spinLock = new SpinLockSimulation(input);
            part(spinLock, 50_000_000);
        });
    }

    private static void part(SpinLock spinLock, int max) {
        IntStream.range(1, max + 1).forEach(spinLock::add);
        System.out.println("Next number after inserting " + max + " is " + spinLock.get());
    }
}
