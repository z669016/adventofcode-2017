package com.putoet.day13;

public interface Layer {
    void init(long delay);
    void next();
    void enter();
    void leave();
    boolean caught();
    int scanner();
    int range();
    void reset();
}
