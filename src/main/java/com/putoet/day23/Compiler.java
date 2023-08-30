package com.putoet.day23;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("SuspiciousNameCombination")
class Compiler {
    public static final Compiler COMPILER = new Compiler();

    private int mulCalls = 0;

    public int mulCalls() { return mulCalls; }

    public List<Consumer<CPU>> compile(@NotNull List<String> program) {
        return program.stream()
                .map(this::compile)
                .collect(Collectors.toList());
    }

    protected Consumer<CPU> compile(@NotNull String instruction) {
        final var parts = instruction.split(" ");
        assert parts.length == 3;

        final var operation = parts[0];
        final var x = parts[1];
        final var y = parts[2];

        return switch (operation) {
            case "set" -> set(x, y);
            case "sub" -> sub(x, y);
            case "mul" -> mul(x, y);
            case "jnz" -> jnz(x, y);
            default -> throw new IllegalArgumentException("Invalid instruction '" + instruction + "'");
        };
    }

    protected Consumer<CPU> set(@NotNull String sx, @NotNull String sy) {
        final var x = sx.charAt(0);
        if (Character.isLetter(sy.charAt(0))) {
            final var y = sy.charAt(0);
            return (cpu -> cpu.set(x, y));
        } else {
            final var y = Integer.parseInt(sy);
            return (cpu -> cpu.set(x, y));
        }
    }

    protected Consumer<CPU> sub(@NotNull String sx, @NotNull String sy) {
        final var x = sx.charAt(0);
        if (Character.isLetter(sy.charAt(0))) {
            final var y = sy.charAt(0);
            return (cpu -> cpu.set(x, cpu.get(x) - cpu.get(y)));
        } else {
            final var y = Integer.parseInt(sy);
            return (cpu -> cpu.set(x, cpu.get(x) - y));
        }
    }

    protected Consumer<CPU> mul(@NotNull String sx, @NotNull String sy) {
        final var x = sx.charAt(0);
        if (Character.isLetter(sy.charAt(0))) {
            final var y = sy.charAt(0);
            return (cpu -> {
                mulCalls++;
                cpu.set(x, cpu.get(x) * cpu.get(y));
            });
        } else {
            final var y = Integer.parseInt(sy);
            return (cpu -> {
                mulCalls++;
                cpu.set(x, cpu.get(x) * y);
            });
        }
    }

    protected Consumer<CPU> jnz(@NotNull String sx, @NotNull String sy) {
        final var x = sx.charAt(0);
        if (x == '0') {
            return (cpu -> {
            });
        } else if (x > '0' && x < '9') {
            if (Character.isLetter(sy.charAt(0))) {
                final var y = sy.charAt(0);
                return (cpu -> cpu.ip(cpu.get(y)));
            } else {
                final var y = Integer.parseInt(sy);
                return (cpu -> cpu.ip(y));
            }
        } else if (Character.isLetter(sy.charAt(0))) {
            final var y = sy.charAt(0);
            return (cpu -> {
                if (cpu.get(x) != 0)
                    cpu.ip(cpu.get(y));
            });
        } else {
            final var y = Integer.parseInt(sy);
            return (cpu -> {
                if (cpu.get(x) != 0)
                    cpu.ip(y);
            });
        }
    }
}
