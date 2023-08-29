package com.putoet.day18;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PairedCPUTest {
    private static final String REG_A = "a";
    private static final String REG_B = "b";

    private PairedCPU compiler;
    private PairedCPU cpu0;
    private PairedCPU cpu1;

    @BeforeEach
    void setup() {
        compiler = new PairedCPU(99);
        cpu0 = new PairedCPU(0);
        cpu1 = new PairedCPU(1);
        cpu0.pair(cpu1);

        cpu0.set(REG_A, 2);
        cpu1.set(REG_B, 5);
    }

    @Test
    void pair() {
        assertEquals(cpu1, cpu0.other());
        assertEquals(cpu1, cpu0.other());

        assertEquals(0L, cpu0.get("p"));
        assertEquals(1L, cpu1.get("p"));
    }

    @Test
    void snd() {
        final var sndConstant = compiler.compile("snd 3");
        final var sndRegister = compiler.compile("snd a");

        assertEquals(0, cpu0.played.size());
        assertEquals(0, cpu0.recovered.size());
        assertEquals(0, cpu1.played.size());
        assertEquals(0, cpu1.recovered.size());

        cpu0.play(sndConstant);
        cpu0.play(sndRegister);

        assertEquals(2, cpu0.played.size());
        assertIterableEquals(List.of(3L, 2L), cpu0.played);
        assertEquals(0, cpu0.recovered.size());
        assertEquals(0, cpu1.played.size());
        assertEquals(2, cpu1.recovered.size());
        assertIterableEquals(List.of(3L, 2L), cpu1.recovered);

        cpu1.play(sndConstant);
        cpu1.play(sndRegister);

        assertEquals(2, cpu0.played.size());
        assertIterableEquals(List.of(3L, 2L), cpu0.played);
        assertEquals(2, cpu0.recovered.size());
        assertIterableEquals(List.of(3L, 0L), cpu0.recovered);
        assertEquals(2, cpu1.played.size());
        assertIterableEquals(List.of(3L, 0L), cpu1.played);
        assertEquals(2, cpu1.recovered.size());
        assertIterableEquals(List.of(3L, 2L), cpu1.recovered);
    }

    @Test
    void rcv() {
        final var rcvA = compiler.compile("rcv a");

        assertFalse(cpu0.play(rcvA));
        assertFalse(cpu0.play(rcvA));
        assertFalse(cpu0.play(rcvA));
        assertFalse(cpu0.play(rcvA));
        assertEquals(2L, cpu0.get(REG_A));
        assertEquals(0L, cpu0.get(REG_B));

        cpu0.recovered.offer(7L);
        assertTrue(cpu0.play(rcvA));
        assertEquals(7L, cpu0.get(REG_A));

        assertFalse(cpu0.play(rcvA));
        assertFalse(cpu0.play(rcvA));
        assertEquals(7L, cpu0.get(REG_A));
        assertEquals(0L, cpu0.get(REG_B));
    }

    @Test
    void play() {
        final var instructions = List.of(
                "snd 1",
                "snd 2",
                "snd p",
                "rcv a",
                "rcv b",
                "rcv c",
                "rcv d");

        final var program = cpu0.compile(instructions);

        boolean prevRunCPU0, prevRunCPU1;
        boolean lastRunCPU0 = false, lastRunCPU1 = false;
        do {
            prevRunCPU0 = lastRunCPU0;
            lastRunCPU0 = cpu0.play(program);
            prevRunCPU1 = lastRunCPU1;
            lastRunCPU1 = cpu1.play(program);
        } while (!(prevRunCPU0 && lastRunCPU0 && prevRunCPU1 && lastRunCPU1));

        assertEquals(3, cpu0.played().size());
        assertEquals(3, cpu1.played().size());

        assertEquals(0, cpu0.get("p"));
        assertEquals(1, cpu0.get("c"));
        assertIterableEquals(List.of(1L, 2L, 0L), cpu0.played());
        assertEquals(1, cpu1.get("p"));
        assertEquals(0, cpu1.get("c"));
        assertIterableEquals(List.of(1L, 2L, 1L), cpu1.played());
    }
}