package com.putoet.day23;

import java.util.List;
import java.util.function.Consumer;

public class CPU {
    public static final char REG_A = 'a';
    public static final char REG_B = 'b';
    public static final char REG_C = 'c';
    public static final char REG_D = 'd';
    public static final char REG_E = 'e';
    public static final char REG_F = 'f';
    public static final char REG_G = 'g';
    public static final char REG_H = 'h';

    private int[] regs = new int[8];
    private int ip;

    private boolean debug = true;

    public boolean isDebug() {
        return debug;
    }

    public void enableDebug() {
        debug = true;
    }

    public void disableDebug() {
        debug = false;
    }

    public int ip() { return ip; }
    public void ip(int offset) { ip += (offset - 1); }

    public void run(List<Consumer<CPU>> program) {
        if (!debug) {
            regs[0] = 1;
        }

        while (ip >=0 && ip < program.size()) {
            run(program.get(ip));
            ip = ip + 1;
        }
    }

    public void run(Consumer<CPU> instruction) {
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
