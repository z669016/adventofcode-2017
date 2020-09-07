package com.putoet.day18;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CPU {
    private final Map<String,Long> registers = new HashMap<>();
    protected final Queue<Long> recovered = new LinkedList<>();
    protected final List<Long> played = new ArrayList<>();
    protected int ip = 0;

    public boolean play(List<Consumer<CPU>> program) {
        boolean stuck = true;
        while ((ip < program.size()) && play(program.get(ip))) {
            stuck = false;
        }
        return stuck || ip >= program.size();
    }

    protected boolean play(Consumer<CPU> instruction) {
        instruction.accept(this);
        ip++;

        return recovered.size() == 0;
    }

    public List<Long> played() {
        return played;
    }

    public List<Long> recovered() {
        return List.copyOf(recovered);
    }

    public long get(String operant) {
        assert operant != null && operant.length() > 0;

        if (isRegister(operant))
            return registers.get(operant);

        return Integer.parseInt(operant);
    }

    public void set(String operant1, long value) {
        if (!isRegister(operant1))
            throw new IllegalArgumentException("Cannot set on a value '" + operant1 + "'");

        registers.put(operant1, value);
    }

    private boolean isRegister(String operant) {
        assert operant != null && operant.length() > 0;

        final boolean isRegister = operant.length() == 1 && Character.isAlphabetic(operant.charAt(0));
        if (isRegister && !registers.containsKey(operant))
            registers.put(operant, 0L);

        return isRegister;
    }

    public List<Consumer<CPU>> compile(List<String> program) {
        return program.stream()
                .map(this::compile)
                .collect(Collectors.toList());
    }

    protected Consumer<CPU> compile(String instruction) {
        return switch (instruction.substring(0, 3)) {
            case "snd" -> snd(instruction);
            case "set" -> set(instruction);
            case "add" -> add(instruction);
            case "mul" -> mul(instruction);
            case "mod" -> mod(instruction);
            case "rcv" -> rcv(instruction);
            case "jgz" -> jgz(instruction);
            default -> throw new IllegalArgumentException("Invalid instruction '" + instruction + "'");
        };
    }

    protected String operant(String instruction, int idx) {
        assert instruction != null && idx > 0 && idx < 3;

        final String[] parts = instruction.split(" ");
        return parts[idx];
    }

    protected Consumer<CPU> snd(String instruction) {
        if (!instruction.startsWith("snd "))
            throw new IllegalArgumentException("Invalid compiler call for snd");

        return (CPU cpu) -> cpu.played.add(cpu.get(cpu.operant(instruction, 1)));
    }

    protected Consumer<CPU> set(String instruction) {
        if (!instruction.startsWith("set "))
            throw new IllegalArgumentException("Invalid compiler call for set");

        return (CPU cpu) -> cpu.set(cpu.operant(instruction, 1), cpu.get(operant(instruction,2 )));
    }

    protected Consumer<CPU> add(String instruction) {
        if (!instruction.startsWith("add "))
            throw new IllegalArgumentException("Invalid compiler call for add");

        return (CPU cpu) -> cpu.set(operant(instruction, 1), cpu.get(operant(instruction, 1)) + cpu.get(operant(instruction,2 )));
    }

    protected Consumer<CPU> mul(String instruction) {
        if (!instruction.startsWith("mul "))
            throw new IllegalArgumentException("Invalid compiler call for mul");

        return (CPU cpu) -> cpu.set(operant(instruction, 1), cpu.get(operant(instruction, 1)) * cpu.get(operant(instruction,2 )));
    }

    protected Consumer<CPU> mod(String instruction) {
        if (!instruction.startsWith("mod "))
            throw new IllegalArgumentException("Invalid compiler call for mod");

        return (CPU cpu) -> cpu.set(operant(instruction, 1), cpu.get(operant(instruction, 1)) % cpu.get(operant(instruction,2 )));
    }

    protected Consumer<CPU> rcv(String instruction) {
        if (!instruction.startsWith("rcv "))
            throw new IllegalArgumentException("Invalid compiler call for rcv");

        return (CPU cpu) -> {
            if (cpu.get(operant(instruction, 1)) > 0) {
                if (cpu.played.size() > 0)
                    cpu.recovered.add(cpu.played.get(cpu.played.size() - 1));
            }
        };
    }

    protected Consumer<CPU> jgz(String instruction) {
        if (!instruction.startsWith("jgz "))
            throw new IllegalArgumentException("Invalid compiler call for jgz");

        return (CPU cpu) -> {
            if (cpu.get(operant(instruction, 1)) > 0) {
                final long offset = cpu.get(operant(instruction, 2));
                if (cpu.ip + offset < 0)
                    throw new IllegalStateException("Invalid instruction '" + instruction + "', jumping outside the program.");

                cpu.ip += offset - 1;
            }
        };
    }
}
