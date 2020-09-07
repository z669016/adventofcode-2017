package com.putoet.day18;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

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
        final Consumer<CPU> sndConstant = compiler.compile("snd 9");
        final Consumer<CPU> sndRegister = compiler.compile("snd a");

        assertEquals(0, cpu.played().size());
        cpu.play(sndConstant);
        assertIterableEquals(List.of(9L), cpu.played());
        cpu.play(sndRegister);
        assertIterableEquals(List.of(9L, 3L), cpu.played());
    }

    @Test
    void set() {
        final Consumer<CPU> setConstant = compiler.compile("set b 5");
        final Consumer<CPU> setRegister = compiler.compile("set a b");

        cpu.play(setConstant);
        assertEquals(3L, cpu.get(REG_A));
        assertEquals(5L, cpu.get(REG_B));
        cpu.play(setRegister);
        assertEquals(5L, cpu.get(REG_A));
        assertEquals(5L, cpu.get(REG_B));
    }

    @Test
    void add() {
        final Consumer<CPU> addConstant = compiler.compile("add b 5");
        final Consumer<CPU> addRegister = compiler.compile("add a b");

        cpu.play(addConstant);
        assertEquals(3L, cpu.get(REG_A));
        assertEquals(6L, cpu.get(REG_B));
        cpu.play(addRegister);
        assertEquals(9L, cpu.get(REG_A));
        assertEquals(6L, cpu.get(REG_B));
    }

    @Test
    void mul() {
        final Consumer<CPU> mulConstant = compiler.compile("mul b 5");
        final Consumer<CPU> mulRegister = compiler.compile("mul a b");

        cpu.play(mulConstant);
        assertEquals(3L, cpu.get(REG_A));
        assertEquals(5L, cpu.get(REG_B));
        cpu.play(mulRegister);
        assertEquals(15L, cpu.get(REG_A));
        assertEquals(5L, cpu.get(REG_B));
    }

    @Test
    void mod() {
        final Consumer<CPU> modConstant = compiler.compile("mod b 5");
        final Consumer<CPU> modRegister = compiler.compile("mod a b");

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
        final Consumer<CPU> rcvConstant = compiler.compile("rcv 0");
        final Consumer<CPU> rcvRegister = compiler.compile("rcv a");

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
        final Consumer<CPU> jgzConstant = compiler.compile("jgz b -2");
        final Consumer<CPU> jgzInvalid = compiler.compile("jgz b -999");
        final Consumer<CPU> noJump = compiler.compile("jgz c a");
        final Consumer<CPU> jgzRegister = compiler.compile("jgz a a");

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
        final List<Consumer<CPU>> program = compiler.compile(ResourceLines.list("/day18.txt"));

        cpu.play(program);
        assertEquals(List.of(4L), cpu.recovered());
    }
}