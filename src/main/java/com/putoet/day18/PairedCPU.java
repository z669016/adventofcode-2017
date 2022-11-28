package com.putoet.day18;

import java.util.function.Consumer;

public class PairedCPU extends CPU {
    private final int id;
    private CPU other;
    private boolean waiting;

    public PairedCPU(int id) {
        this.id = id;
        set("p", id);
    }

    public void pair(PairedCPU other) {
        assert other != null;

        this.other = other;
        other.other = this;
    }

    public CPU other() { return other; }

    protected boolean play(Consumer<CPU> instruction) {
        waiting = false;

        instruction.accept(this);
        ip++;

        return !waiting;
    }

    protected Consumer<CPU> snd(String instruction) {
        return (CPU c) -> {
            final PairedCPU cpu = (PairedCPU) c;
            if (cpu.other != null) {
                final long value = cpu.get(operand(instruction, 1));
                cpu.played.add(value);
                cpu.other.recovered.offer(value);
            }
        };
    }

    protected Consumer<CPU> rcv(String instruction) {
        return (CPU c) -> {
            final PairedCPU cpu = (PairedCPU) c;
            if (cpu.recovered.isEmpty()) {
                cpu.waiting = true;
                cpu.ip--;
            } else {
                cpu.set(operand(instruction, 1), cpu.recovered.poll());
            }
        };
    }
}
