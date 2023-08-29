package com.putoet.day18;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    public static final String REG_A = "a";
    public static final String REG_B = "b";
    private CPU compiler;
    private CPU cpu;

    @BeforeEach
    void setup() {
        compiler = new CPU();
        cpu = new CPU();
        cpu.set(REG_A, 3);
        cpu.set(REG_B, 1);
    }

    @Test
    void compile() {
        assertThrows(IllegalArgumentException.class, () -> compiler.compile("bla a b"));
    }

    @Test
    void snd() {
        final var sndConstant = compiler.compile("snd 9");
        final var sndRegister = compiler.compile("snd a");

        assertEquals(0, cpu.played().size());
        cpu.play(sndConstant);
        assertIterableEquals(List.of(9L), cpu.played());
        cpu.play(sndRegister);
        assertIterableEquals(List.of(9L, 3L), cpu.played());
    }

    @Test
    void set() {
        final var setConstant = compiler.compile("set b 5");
        final var setRegister = compiler.compile("set a b");

        cpu.play(setConstant);
        assertEquals(3L, cpu.get(REG_A));
        assertEquals(5L, cpu.get(REG_B));
        cpu.play(setRegister);
        assertEquals(5L, cpu.get(REG_A));
        assertEquals(5L, cpu.get(REG_B));
    }

    @Test
    void add() {
        final var addConstant = compiler.compile("add b 5");
        final var addRegister = compiler.compile("add a b");

        cpu.play(addConstant);
        assertEquals(3L, cpu.get(REG_A));
        assertEquals(6L, cpu.get(REG_B));
        cpu.play(addRegister);
        assertEquals(9L, cpu.get(REG_A));
        assertEquals(6L, cpu.get(REG_B));
    }

    @Test
    void mul() {
        final var mulConstant = compiler.compile("mul b 5");
        final var mulRegister = compiler.compile("mul a b");

        cpu.play(mulConstant);
        assertEquals(3L, cpu.get(REG_A));
        assertEquals(5L, cpu.get(REG_B));
        cpu.play(mulRegister);
        assertEquals(15L, cpu.get(REG_A));
        assertEquals(5L, cpu.get(REG_B));
    }

    @Test
    void mod() {
        final var modConstant = compiler.compile("mod b 5");
        final var modRegister = compiler.compile("mod a b");

        cpu.set(REG_B, 5);

        cpu.play(modConstant);
        assertEquals(3L, cpu.get(REG_A));
        assertEquals(0L, cpu.get(REG_B));

        cpu.set(REG_A, 13);
        cpu.set(REG_B, 5);
        cpu.play(modRegister);

        assertEquals(3L, cpu.get(REG_A));
        assertEquals(5L, cpu.get(REG_B));
    }

    @Test
    void rcv() {
        final var rcvConstant = compiler.compile("rcv 0");
        final var rcvRegister = compiler.compile("rcv a");

        cpu.played.add(7L);
        assertEquals(0, cpu.recovered().size());
        assertTrue(cpu.play(rcvConstant));
        assertEquals(0, cpu.recovered().size());
        assertFalse(cpu.play(rcvRegister));
        assertEquals(1, cpu.recovered().size());
        assertIterableEquals(List.of(7L), cpu.recovered());
    }

    @Test
    void jgz() {
        final var jgzConstant = compiler.compile("jgz b -2");
        final var jgzInvalid = compiler.compile("jgz b -999");
        final var noJump = compiler.compile("jgz c a");
        final var jgzRegister = compiler.compile("jgz a a");

        assertEquals(0, cpu.ip);
        cpu.play(noJump);
        assertEquals(1, cpu.ip);
        cpu.play(jgzRegister);
        assertEquals(4, cpu.ip);
        cpu.play(jgzConstant);
        assertEquals(2, cpu.ip);

        assertThrows(IllegalStateException.class, () -> cpu.play(jgzInvalid));
    }

    @Test
    void play() {
        final var program = compiler.compile(ResourceLines.list("/day18.txt"));

        cpu.play(program);
        assertEquals(List.of(4L), cpu.recovered());
    }
}