package com.putoet.day23;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day23 {
    public static void main(String[] args) {
        Timer.run(() -> {
            final var compiler = new Compiler();
            final var program = compiler.compile(ResourceLines.list("/day23.txt"));

            CPU cpu = new CPU();
            cpu.run(program);
            System.out.println("Instruction mul was called " + compiler.mulCalls() + " times");
        });

        Timer.run(() -> System.out.println("The value of register h is " + part2()));
    }

    public static int part2() {
        int b, c, d, f, g, h = 0;

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
