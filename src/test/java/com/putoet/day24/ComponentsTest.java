package com.putoet.day24;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComponentsTest {
    private Components components;

    @BeforeEach
    void setup() {
        components = Components.of(ResourceLines.list("/day24.txt"));
    }

    @Test
    void of() {
        assertEquals(8, components.size());
    }

    @Test
    void forPort() {
        final List<Component> list = components.forPort(0);
        assertEquals(2, list.size());
        assertEquals("[0/2, 0/1]", list.toString());
    }
}