package com.putoet.day8;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    private static final String A = "a";
    private static final String B = "b";

    @Test
    void getRegister() {
        final var cpu = new CPU();
        assertEquals(0, cpu.getRegister(A));
    }

    @Test
    void incRegister() {
        final var cpu = new CPU();

        cpu.incRegister(A, 7, c -> true);
        assertEquals(7, cpu.getRegister(A));

        cpu.incRegister(A, 2, c -> c.getRegister(A) == 0);
        assertEquals(7, cpu.getRegister(A));

        cpu.incRegister(A, 2, c -> c.getRegister(A) == 7);
        assertEquals(9, cpu.getRegister(A));
    }

    @Test
    void decRegister() {
        final var cpu = new CPU();

        cpu.decRegister(B, 2, c -> true);
        assertEquals(-2, cpu.getRegister(B));

        cpu.decRegister(B, -7, c -> c.getRegister(B) == 0);
        assertEquals(-2, cpu.getRegister(B));

        cpu.decRegister(B, -7, c -> c.getRegister(B) == -2);
        assertEquals(5, cpu.getRegister(B));
    }

    @Test
    void run() {
        final var lines = ResourceLines.list("/day8.txt");
        final var cpu = CPU.run(lines);

        final var max = cpu.highestRegisterValue().orElseThrow();
        assertEquals(1, max);

        final var highestEVer = cpu.highestRegisterValueEver().orElseThrow();
        assertEquals(10, highestEVer);
    }
}