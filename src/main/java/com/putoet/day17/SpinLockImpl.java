package com.putoet.day17;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SpinLockImpl implements SpinLock {
    private final List<Integer> lock = new ArrayList<>(List.of(0));
    private final int spinner;
    private int current = 0;

    public SpinLockImpl(int spinner) {
        assert spinner > 0;

        this.spinner = spinner;
    }

    @Override
    public int get() {
        return lock.get((current + 1) % lock.size());
    }

    private void spin() {
        current = (current + spinner) % lock.size();
    }

    @Override
    public void add(int value) {
        spin();
        current += 1;
        lock.add(current, value);
    }

    public List<Integer> list() {
        return Collections.unmodifiableList(lock);
    }
}
