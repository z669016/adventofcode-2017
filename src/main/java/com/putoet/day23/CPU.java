package com.putoet.day23;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

class CPU {
    public static final char REG_A = 'a';
    public static final char REG_B = 'b';
    public static final char REG_C = 'c';
    public static final char REG_D = 'd';
    public static final char REG_E = 'e';

    private final int[] regs = new int[8];
    private int ip;

    public int ip() { return ip; }
    public void ip(int offset) { ip += (offset - 1); }

    public void run(@NotNull List<Consumer<CPU>> program) {
        ip = 0;

        while (ip >=0 && ip < program.size()) {
            run(program.get(ip));
            ip = ip + 1;
        }
    }

    public void run(@NotNull Consumer<CPU> instruction) {
        instruction.accept(this);
    }

    protected int get(char x) {
        return regs[x - 'a'];
    }

    protected void set(char x, char y) {
        regs[x - 'a'] = regs[y - 'a'];
    }

    protected void set(char x, int value) {
        regs[x - 'a'] = value;
    }
}
