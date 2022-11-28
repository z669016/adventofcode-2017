package com.putoet.day17;

import java.util.List;

public class SpinLockSimulation implements SpinLock {
    private final int value0 = 0;
    private int value1;
    private int size = 1;
    private int current = 0;
    private final int spinner;

    public SpinLockSimulation(int spinner) {
        assert spinner > 0;

        this.spinner = spinner;
    }

    @Override
    public int get() {
        return size > 1 ? value1 : value0;
    }

    private void spin() {
        current = (current + spinner) % size;
    }

    @Override
    public void add(int value) {
        spin();
        current += 1;
        size += 1;
        if (current == 1)
            value1 = value;
    }

    public List<Integer> list() {
        return List.of(value0, value1);
    }
}
