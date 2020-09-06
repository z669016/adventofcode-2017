package com.putoet.day17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SpinLockImplTest {
    private SpinLock spinLock;

    @BeforeEach
    void setup() {
        spinLock = new SpinLockImpl(3);
    }

    @Test
    void add() {
        assertEquals(List.of(0), spinLock.list());
        spinLock.add(1);
        assertEquals(List.of(0, 1), spinLock.list());
        spinLock.add(2);
        assertEquals(List.of(0, 2, 1), spinLock.list());
        spinLock.add(3);
        assertEquals(List.of(0, 2, 3, 1), spinLock.list());
        spinLock.add(4);
        assertEquals(List.of(0, 2, 4, 3, 1), spinLock.list());
        spinLock.add(5);
        assertEquals(List.of(0, 5, 2, 4, 3, 1), spinLock.list());
        spinLock.add(6);
        assertEquals(List.of(0, 5, 2, 4, 3, 6, 1), spinLock.list());
        spinLock.add(7);
        assertEquals(List.of(0, 5, 7, 2, 4, 3, 6, 1), spinLock.list());
        spinLock.add(8);
        assertEquals(List.of(0, 5, 7, 2, 4, 3, 8, 6, 1), spinLock.list());
        spinLock.add(9);
        assertEquals(List.of(0, 9, 5, 7, 2, 4, 3, 8, 6, 1), spinLock.list());
    }

    @Test
    void test2017() {
        IntStream.range(1, 2018).forEach(spinLock::add);
        assertEquals(638, spinLock.get());
        assertEquals(0, spinLock.list().get(0));
        assertEquals(1226, spinLock.list().get(1));
    }
}