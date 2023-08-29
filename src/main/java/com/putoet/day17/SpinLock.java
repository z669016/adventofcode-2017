package com.putoet.day17;

import java.util.List;

interface SpinLock {
    int get();

    void add(int value);

    List<Integer> list();
}
