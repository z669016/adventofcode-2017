package com.putoet.day23;

import com.putoet.resources.ResourceLines;

import java.util.List;
import java.util.function.Consumer;

public class Day23 {
    public static void main(String[] args) {
        final Compiler compiler = new Compiler();
        final List<Consumer<CPU>> program = compiler.compile(ResourceLines.list("/day23.txt"));

        CPU cpu = new CPU();
        cpu.run(program);
        System.out.println("Instruction mul was called " + compiler.mulCalls() + " times");

        System.out.println("The value of register h is " + part2());
    }

    public static int part2() {
        int b, c, d, e, f, g, h = 0;

        // initial values
        b = 106_700;
        c = b + 17_000;

        // count all non primes between b and c
        do {
            f = 1;
            d = 2;
            while (d < b) {
                if (b % d == 0) {
                    f = 0;
                    break;
                }
                d++;
            }

            if (f == 0) {
                h++;
            }
            g = b - c;
            b += 17;
        } while (g != 0);

        return h;
    }
}
