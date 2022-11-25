package com.putoet.day8;

import com.putoet.resources.ResourceLines;

import java.util.List;
import java.util.OptionalInt;

public class Day8 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day8.txt");
        final CPU cpu = CPU.run(lines);

        final OptionalInt max = cpu.highestRegisterValue();
        if (max.isPresent())
            System.out.println("Maximum register value is " + max.getAsInt());

        final OptionalInt ever = cpu.highestRegisterValueEver();
        if (ever.isPresent())
            System.out.println("Maximum register value ever is " + ever.getAsInt());
    }
}
