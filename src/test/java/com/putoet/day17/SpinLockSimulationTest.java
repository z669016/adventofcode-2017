package com.putoet.day17;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SpinLockSimulationTest {
    @Test
    void test2017() {
        final var spinLock = new SpinLockSimulation(3);

        IntStream.range(1, 2018).forEach(spinLock::add);
        assertEquals(0, spinLock.list().get(0));
        assertEquals(1226, spinLock.list().get(1));
    }
}