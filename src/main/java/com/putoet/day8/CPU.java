package com.putoet.day8;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;

class CPU {
    private enum Operator {
        EQUALS("=="),
        NOT_EQUALS("!="),
        LESS_THAN("<"),
        LESS_EQUAL("<="),
        GREATER_THAN(">"),
        GREATER_EQUAL(">=");

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        static Operator of(String symbol) {
            for (var operator : Operator.values()) {
                if (operator.symbol.equals(symbol))
                    return operator;
            }
            throw new IllegalArgumentException("Invalid symbol '" + symbol + "'");
        }
    }

    private final Map<String,Integer> regs = new HashMap<>();
    private int highestEver = Integer.MIN_VALUE;

    public OptionalInt highestRegisterValue() {
        return regs.values().stream().mapToInt(i -> i).max();
    }

    public OptionalInt highestRegisterValueEver() {
        return regs.isEmpty() ? OptionalInt.empty() : OptionalInt.of(highestEver);
    }

    public int getRegister(@NotNull String name) {
        if (!regs.containsKey(name))
            setRegister(name, 0);

        return regs.get(name);
    }

    public void incRegister(@NotNull String name, int value, @NotNull Predicate<CPU> predicate) {
        if (predicate.test(this))
            setRegister(name, getRegister(name) + value);
    }

    public void decRegister(@NotNull String name, int value, @NotNull Predicate<CPU> predicate) {
        if (predicate.test(this))
            setRegister(name, getRegister(name) - value);
    }

    private void setRegister(@NotNull String name, int value) {
        if (value > highestEver)
            highestEver = value;

        regs.put(name, value);
    }

    public static Predicate<CPU> compile(@NotNull String predicate) {
        final String[] parts = predicate.split(" ");
        if (parts.length != 3)
            throw new IllegalArgumentException("Invalid predicate '" + predicate + "'");

        return compile(parts[0], Operator.of(parts[1]), Integer.parseInt(parts[2]));
    }

    private static Predicate<CPU> compile(String reg, Operator operator, int value) {
        return switch (operator) {
            case EQUALS -> (CPU cpu) -> cpu.getRegister(reg) == value;
            case NOT_EQUALS -> (CPU cpu) -> cpu.getRegister(reg) != value;
            case GREATER_THAN -> (CPU cpu) -> cpu.getRegister(reg) > value;
            case GREATER_EQUAL -> (CPU cpu) -> cpu.getRegister(reg) >= value;
            case LESS_THAN -> (CPU cpu) -> cpu.getRegister(reg) < value;
            case LESS_EQUAL -> (CPU cpu) -> cpu.getRegister(reg) <= value;
        };
    }

    public static CPU run(@NotNull List<String> instructions) {
        final var cpu = new CPU();

        instructions.forEach(instruction -> {
            final var parts = instruction.split(" if ");
            if (parts.length != 2)
                throw new IllegalArgumentException("Invalid instruction '" + instruction + "'");

            final var predicate = compile(parts[1]);
            final var actions = parts[0].split(" ");
            final var register = actions[0].trim();
            final var action = actions[1].trim();
            final var value = Integer.parseInt(actions[2].trim());

            switch (action) {
                case "inc" -> cpu.incRegister(register, value, predicate);
                case "dec" -> cpu.decRegister(register, value, predicate);
                default -> throw new IllegalArgumentException("Invalid instruction '" + instruction + "'");
            }
        });

        return cpu;
    }
}
