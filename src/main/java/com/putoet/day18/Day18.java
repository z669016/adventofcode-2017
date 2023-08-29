package com.putoet.day18;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day18 {
    public static void main(String[] args) {
        Timer.run(() -> {
            final var cpu = new CPU();
            final var program = cpu.compile(ResourceLines.list("/day18.txt"));

            cpu.play(program);
            System.out.println("First recovered played value is " + cpu.recovered().get(0));
        });

        Timer.run(() -> {
            final var cpu0 = new PairedCPU(0);
            final var cpu1 = new PairedCPU(1);
            cpu0.pair(cpu1);

            var program = cpu0.compile(ResourceLines.list("/day18.txt"));

            boolean prevRunCPU0, prevRunCPU1;
            boolean lastRunCPU0 = false, lastRunCPU1 = false;
            do {
                prevRunCPU0 = lastRunCPU0;
                lastRunCPU0 = cpu0.play(program);
                prevRunCPU1 = lastRunCPU1;
                lastRunCPU1 = cpu1.play(program);
            } while (!(prevRunCPU0 && lastRunCPU0 && prevRunCPU1 && lastRunCPU1));

            System.out.println("Program 1 send " + cpu1.played.size() + " values");
        });
    }
}
