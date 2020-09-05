package com.putoet.day15;

public class Judge {
    private int count = 0;

    void compare(long a, long b) {
        if ((a & 0xffff) == (b & 0xffff)) count++;
    }

    public long count() { return count; }
}
