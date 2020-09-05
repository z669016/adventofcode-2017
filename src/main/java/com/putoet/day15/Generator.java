package com.putoet.day15;

import java.util.function.Supplier;

public class Generator implements Supplier<Long> {
    private final String name;
    private final long factor;
    private long lastNumber;

    public Generator(String name, long factor, long startValue) {
        this.name = name;
        this.factor = factor;
        this.lastNumber = startValue;
    }

    @Override
    public Long get() {
        return lastNumber = (lastNumber * factor) % Integer.MAX_VALUE;
    }
}
