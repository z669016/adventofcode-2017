package com.putoet.day1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntProducerTest {

    @Test
    void of() {
        final var producer = IntProducer.of("12345");

        assertTrue(producer.hasNext());

        assertEquals(1, producer.get());
        assertEquals(2, producer.next());
        assertEquals(2, producer.next());
        assertEquals(2, producer.get());
        assertEquals(3, producer.next());
        assertEquals(3, producer.get());
        assertEquals(4, producer.next());
        assertEquals(4, producer.get());
        assertEquals(5, producer.next());
        assertEquals(5, producer.get());
        assertEquals(1, producer.next());
        assertEquals(1, producer.next());

        assertFalse(producer.hasNext());

        assertThrows(IllegalStateException.class, producer::get);
    }
}