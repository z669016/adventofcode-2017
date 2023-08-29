package com.putoet.day15;

import java.util.function.Supplier;

class Generator implements Supplier<Long> {
    private final long factor;
    private long lastNumber;

    public Generator(long factor, long startValue) {
        this.factor = factor;
        this.lastNumber = startValue;
    }

    @Override
    public Long get() {
        return lastNumber = (lastNumber * factor) % Integer.MAX_VALUE;
    }
}
