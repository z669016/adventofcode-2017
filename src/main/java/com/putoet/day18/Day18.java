package com.putoet.day18;

import com.putoet.resources.ResourceLines;

import java.util.List;
import java.util.function.Consumer;

public class Day18 {
    public static void main(String[] args) {
        final CPU cpu = new CPU();
        List<Consumer<CPU>> program = cpu.compile(ResourceLines.list("/day18.txt"));

        cpu.play(program);
        System.out.println("First recovered played value is " + cpu.recovered().get(0));

        final PairedCPU cpu0 = new PairedCPU(0);
        final PairedCPU cpu1 = new PairedCPU(1);
        cpu0.pair(cpu1);

        program = cpu0.compile(ResourceLines.list("/day18.txt"));

        boolean prevRunCPU0, prevRunCPU1;
        boolean lastRunCPU0 = false, lastRunCPU1 = false;
        do {
            prevRunCPU0 = lastRunCPU0;
            lastRunCPU0 = cpu0.play(program);
            prevRunCPU1 = lastRunCPU1;
            lastRunCPU1 = cpu1.play(program);
        } while (!(prevRunCPU0 && lastRunCPU0 && prevRunCPU1 && lastRunCPU1));

        System.out.println("Program 1 send " + cpu1.played.size() + " values");
    }
}
