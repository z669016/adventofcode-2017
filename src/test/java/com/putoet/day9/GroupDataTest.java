package com.putoet.day9;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupDataTest {

    @Test
    void get() {
        final GroupData data = new GroupData("{{<!>},{<!>},{<!>},{<a>}}");
        for (int idx = 0; idx < 25; idx++) {
            assertNotEquals(GroupData.EOF, data.get());
        }
        assertEquals(GroupData.EOF, data.get());
        assertEquals(GroupData.EOF, data.get());
        assertEquals(GroupData.EOF, data.get());
    }

    @Test
    void unget() {
        final GroupData data = new GroupData("123");
        assertEquals('1', data.get());
        assertEquals('2', data.get());
        assertEquals('3', data.get());

        data.unget();
        data.unget();

        assertEquals('2', data.get());
    }
}