package com.putoet.day9;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTokenizerTest {
    @Test
    void next() {
        final DataTokenizer data = new DataTokenizer("{{<!>},{<!>},{<!>},{<a>}}");
        for (int idx = 0; idx < 25; idx++) {
            assertTrue(data.hasNext());
            data.next();
        }
        assertFalse(data.hasNext());
        assertThrows(IllegalStateException.class, data::next);
    }
}