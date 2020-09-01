package com.putoet.day8;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    private static final String A = "a";
    private static final String B = "b";

    @Test
    void getRegister() {
        final CPU cpu = new CPU();
        assertEquals(0, cpu.getRegister(A));
    }

    @Test
    void incRegister() {
        final CPU cpu = new CPU();

        cpu.incRegister(A, 7, c -> true);
        assertEquals(7, cpu.getRegister(A));

        cpu.incRegister(A, 2, c -> c.getRegister(A) == 0);
        assertEquals(7, cpu.getRegister(A));

        cpu.incRegister(A, 2, c -> c.getRegister(A) == 7);
        assertEquals(9, cpu.getRegister(A));
    }

    @Test
    void decRegister() {
        final CPU cpu = new CPU();

        cpu.decRegister(B, 2, c -> true);
        assertEquals(-2, cpu.getRegister(B));

        cpu.decRegister(B, -7, c -> c.getRegister(B) == 0);
        assertEquals(-2, cpu.getRegister(B));

        cpu.decRegister(B, -7, c -> c.getRegister(B) == -2);
        assertEquals(5, cpu.getRegister(B));
    }

    @Test
    void run() {
        final List<String> lines = ResourceLines.list("/day8.txt");
        final CPU cpu = CPU.run(lines);

        final OptionalInt max = cpu.highestRegisterValue();
        assertTrue(max.isPresent());
        assertEquals(1, max.getAsInt());

        final OptionalInt highestEVer = cpu.highestRegisterValueEVer();
        assertTrue(highestEVer.isPresent());
        assertEquals(10, highestEVer.getAsInt());
    }
}