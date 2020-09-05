package com.putoet.day15;

public class MultipleOfGenerator extends Generator {
    private final long multiple;

    public MultipleOfGenerator(String name, long factor, long startValue, int multiple) {
        super(name, factor, startValue);

        if (multiple != 4 && multiple != 8)
            throw new IllegalArgumentException("The MultipleOfGenerator only works for values 4 and 8");

        this.multiple = multiple;
    }

    @Override
    public Long get() {
        long get = super.get();
        while ((get % multiple) != 0)
            get = super.get();
        return get;
    }
}
