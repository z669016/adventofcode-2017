package com.putoet.day15;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultipleOfGeneratorTest {
    @Test
    void get() {
        final Generator a = new MultipleOfGenerator("A", 16807, 65, 4);
        final Generator b = new MultipleOfGenerator("B", 48271, 8921, 8);

        assertEquals(1352636452, a.get());
        assertEquals(1992081072, a.get());
        assertEquals(530830436, a.get());
        assertEquals(1980017072, a.get());
        assertEquals(740335192, a.get());

        assertEquals(1233683848, b.get());
        assertEquals(862516352, b.get());
        assertEquals(1159784568, b.get());
        assertEquals(1616057672, b.get());
        assertEquals(412269392, b.get());
    }
}