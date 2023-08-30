package com.putoet.day23;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    public static final int INIT_A = 11;
    public static final int INIT_D = 3;
    private CPU cpu;

    @BeforeEach
    void setup() {
        cpu = new CPU();
        cpu.set(CPU.REG_A, INIT_A);
        cpu.set(CPU.REG_D, INIT_D);
    }

    @Test
    void set() {
        var instruction = Compiler.COMPILER.compile("set c 9");
        cpu.run(instruction);
        assertEquals(9, cpu.get(CPU.REG_C));

        instruction = Compiler.COMPILER.compile("set c a");
        cpu.run(instruction);
        assertEquals(INIT_A, cpu.get(CPU.REG_C));
    }

    @Test
    void sub() {
        var instruction = Compiler.COMPILER.compile("sub a 1");
        cpu.run(instruction);
        assertEquals(INIT_A - 1, cpu.get(CPU.REG_A));

        instruction = Compiler.COMPILER.compile("sub a d");
        cpu.run(instruction);
        assertEquals(INIT_A - 1 - INIT_D, cpu.get(CPU.REG_A));
    }

    @Test
    void mul() {
        var instruction = Compiler.COMPILER.compile("mul a 2");
        cpu.run(instruction);
        assertEquals(INIT_A * 2, cpu.get(CPU.REG_A));

        instruction = Compiler.COMPILER.compile("mul a d");
        cpu.run(instruction);
        assertEquals((INIT_A * 2) * INIT_D, cpu.get(CPU.REG_A));
    }

    @Test
    void jnz() {
        var instruction = Compiler.COMPILER.compile("jnz b 11");
        cpu.run(instruction);
        assertEquals(0, cpu.ip());

        instruction = Compiler.COMPILER.compile("jnz a 11");
        cpu.run(instruction);
        assertEquals(10, cpu.ip());

        instruction = Compiler.COMPILER.compile("jnz a -1");
        cpu.run(instruction);
        assertEquals(8, cpu.ip());

        instruction = Compiler.COMPILER.compile("jnz a d");
        cpu.run(instruction);
        assertEquals(7 + INIT_D, cpu.ip());

        instruction = Compiler.COMPILER.compile("jnz 0 2");
        cpu.run(instruction);
        assertEquals(7 + INIT_D, cpu.ip());

        instruction = Compiler.COMPILER.compile("jnz 1 d");
        cpu.run(instruction);
        assertEquals(6 + 2 * INIT_D, cpu.ip());

    }
}