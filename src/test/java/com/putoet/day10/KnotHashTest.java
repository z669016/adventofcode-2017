package com.putoet.day10;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnotHashTest {
    @Test
    void create() {
        final KnotHash knotHash = new KnotHash();
        final List<Integer> list = knotHash.asList();

        assertEquals(256, list.size());
        assertEquals(0, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(255, list.get(255));
    }

    @Test
    void reverse() {
        final KnotHash list = new KnotHash(new int[] {0, 1, 2, 3, 4});

        list.reverse(3);
        assertEquals(List.of(2, 1, 0, 3, 4), list.asList());

        list.reverse(4);
        assertEquals(List.of(4, 3, 0, 1, 2), list.asList());

        list.reverse(1);
        assertEquals(List.of(4, 3, 0, 1, 2), list.asList());

        list.reverse(5);
        assertEquals(List.of(3, 4, 2, 1, 0), list.asList());

        assertEquals(12, list.checksum());
    }
}