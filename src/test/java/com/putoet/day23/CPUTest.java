package com.putoet.day23;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    private CPU cpu;

    @BeforeEach
    void setup() {
        cpu = new CPU();
    }

    @Test
    void run() {
        final List<Consumer<CPU>> program = List.of(
                (c -> assertEquals(cpu, c)),
                (c -> c.set(CPU.REG_E, -2))
        );

        cpu.run(program);
    }

    @Test
    void get() {
        assertEquals(0, cpu.get(CPU.REG_B));
        cpu.set(CPU.REG_B, 7);
        assertEquals(7, cpu.get(CPU.REG_B));
    }
}