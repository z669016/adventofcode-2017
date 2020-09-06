package com.putoet.day17;

import java.util.List;

public interface SpinLock {
    int get();

    void add(int value);

    List<Integer> list();
}
