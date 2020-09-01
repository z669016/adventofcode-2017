package com.putoet.day8;

import java.util.*;
import java.util.function.Predicate;

public class CPU {
    private final Map<String,Integer> regs = new HashMap<>();
    private int highestEver = Integer.MIN_VALUE;

    public Map<String,Integer> regs() {
        return Collections.unmodifiableMap(regs);
    }

    public OptionalInt highestRegisterValue() {
        return regs.values().stream().mapToInt(i -> i).max();
    }

    public OptionalInt highestRegisterValueEVer() {
        return regs.size() == 0 ? OptionalInt.empty() : OptionalInt.of(highestEver);
    }

    public int getRegister(String name) {
        assert name != null;

        if (!regs.containsKey(name))
            setRegister(name, 0);

        return regs.get(name);
    }

    public void incRegister(String name, int value, Predicate<CPU> predicate) {
        if (predicate.test(this))
            setRegister(name, getRegister(name) + value);
    }

    public void decRegister(String name, int value, Predicate<CPU> predicate) {
        if (predicate.test(this))
            setRegister(name, getRegister(name) - value);
    }

    private void setRegister(String name, int value) {
        if (value > highestEver)
            highestEver = value;

        regs.put(name, value);
    }

    public static Predicate<CPU> predicate(String predicate) {
        assert predicate != null;

        final String[] parts = predicate.split(" ");
        if (parts.length != 3)
            throw new IllegalArgumentException("Invalid predicate '" + predicate + "'");

        return switch (parts[1]) {
            case "==" -> (CPU cpu) -> cpu.getRegister(parts[0]) == Integer.parseInt(parts[2]);
            case "!=" -> (CPU cpu) -> cpu.getRegister(parts[0]) != Integer.parseInt(parts[2]);
            case ">" -> (CPU cpu) -> cpu.getRegister(parts[0]) > Integer.parseInt(parts[2]);
            case ">=" -> (CPU cpu) -> cpu.getRegister(parts[0]) >= Integer.parseInt(parts[2]);
            case "<" -> (CPU cpu) -> cpu.getRegister(parts[0]) < Integer.parseInt(parts[2]);
            case "<=" -> (CPU cpu) -> cpu.getRegister(parts[0]) <= Integer.parseInt(parts[2]);
            default -> throw new IllegalArgumentException("Invalid predicate '" + predicate + "'");
        };
    }

    public static CPU run(List<String> instructions) {
        final CPU cpu = new CPU();

        instructions.forEach(instruction -> {
            final String[] parts = instruction.split(" if ");
            if (parts.length != 2)
                throw new IllegalArgumentException("Invalid instruction '" + instruction + "'");

            final Predicate<CPU> predicate = predicate(parts[1]);

            final String[] actions = parts[0].split(" ");
            final String register = actions[0].trim();
            final String action = actions[1].trim();
            final int value = Integer.parseInt(actions[2].trim());

            switch (action) {
                case "inc" -> cpu.incRegister(register, value, predicate);
                case "dec" -> cpu.decRegister(register, value, predicate);
                default -> throw new IllegalArgumentException("Invalid instruction '" + instruction + "'");
            }
        });

        return cpu;
    }
}
