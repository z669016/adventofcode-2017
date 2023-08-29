package com.putoet.day8;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day8 {
    public static void main(String[] args) {
        final var lines = ResourceLines.list("/day8.txt");

        Timer.run(() -> {
            final var cpu = CPU.run(lines);
            System.out.println("Maximum register value is " + cpu.highestRegisterValue().orElseThrow());
            System.out.println("Maximum register value ever is " + cpu.highestRegisterValueEver().orElseThrow());
        });
    }
}
