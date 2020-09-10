package com.putoet.day23;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Compiler {
    public static final Compiler COMPILER = new Compiler();

    private int mulCalls = 0;

    public int mulCalls() { return mulCalls; }

    public List<Consumer<CPU>> compile(List<String> program) {
        assert program != null && program.size() > 0;

        return program.stream()
                .map(this::compile)
                .collect(Collectors.toList());
    }

    protected Consumer<CPU> compile(String instruction) {
        final String[] parts = instruction.split(" ");
        assert parts.length == 3;

        final String operation = parts[0];
        final String x = parts[1];
        final String y = parts[2];

        return switch (operation) {
            case "set" -> set(x, y);
            case "sub" -> sub(x, y);
            case "mul" -> mul(x, y);
            case "jnz" -> jnz(x, y);
            default -> throw new IllegalArgumentException("Invalid instruction '" + instruction + "'");
        };
    }

    protected Consumer<CPU> set(String sx, String sy) {
        final char x = sx.charAt(0);
        if (Character.isLetter(sy.charAt(0))) {
            final char y = sy.charAt(0);
            return (cpu -> {
                cpu.set(x, y);
            });
        } else {
            final int y = Integer.parseInt(sy);
            return (cpu -> {
                cpu.set(x, y);
            });
        }
    }

    protected Consumer<CPU> sub(String sx, String sy) {
        final char x = sx.charAt(0);
        if (Character.isLetter(sy.charAt(0))) {
            final char y = sy.charAt(0);
            return (cpu -> {
                cpu.set(x, cpu.get(x) - cpu.get(y));
            });
        } else {
            final int y = Integer.parseInt(sy);
            return (cpu -> {
                cpu.set(x, cpu.get(x) - y);
            });
        }
    }

    protected Consumer<CPU> mul(String sx, String sy) {
        final char x = sx.charAt(0);
        if (Character.isLetter(sy.charAt(0))) {
            final char y = sy.charAt(0);
            return (cpu -> {
                mulCalls++;
                cpu.set(x, cpu.get(x) * cpu.get(y));
            });
        } else {
            final int y = Integer.parseInt(sy);
            return (cpu -> {
                mulCalls++;
                cpu.set(x, cpu.get(x) * y);
            });
        }
    }

    protected Consumer<CPU> jnz(String sx, String sy) {
        final char x = sx.charAt(0);
        if (x == '0') {
            return (cpu -> {
            });
        } else if (x > '0' && x < '9') {
            if (Character.isLetter(sy.charAt(0))) {
                final char y = sy.charAt(0);
                return (cpu -> cpu.ip(cpu.get(y)));
            } else {
                final int y = Integer.parseInt(sy);
                return (cpu -> cpu.ip(y));
            }
        } else if (Character.isLetter(sy.charAt(0))) {
            final char y = sy.charAt(0);
            return (cpu -> {
                if (cpu.get(x) != 0)
                    cpu.ip(cpu.get(y));
            });
        } else {
            final int y = Integer.parseInt(sy);
            return (cpu -> {
                if (cpu.get(x) != 0)
                    cpu.ip(y);
            });
        }
    }
}
