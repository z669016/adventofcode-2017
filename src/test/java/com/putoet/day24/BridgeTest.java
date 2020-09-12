package com.putoet.day24;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BridgeTest {
    private Components components;
    private List<Component> starters;

    @BeforeEach
    void setup() {
        components = Components.of(ResourceLines.list("/day24.txt"));
        starters = components.forPort(0);
    }

    @Test
    void bridge() {
        final Bridge b1 = new Bridge(starters.get(0), components);
        assertEquals(1, b1.length());
        assertEquals(2, b1.strength());

        final Bridge b2 = new Bridge(starters.get(1), components);
        assertEquals(1, b2.length());
        assertEquals(1, b2.strength());
    }

    @Test
    void options() {
        final Bridge b1 = new Bridge(starters.get(0), components);
        final List<Bridge> options = b1.options();
        assertEquals(2, options.size());
        assertEquals(6, options.get(0).strength());
        assertEquals(2, options.get(0).length());

        System.out.println(options);
    }
}