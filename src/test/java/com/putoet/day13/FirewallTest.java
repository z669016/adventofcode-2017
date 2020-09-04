package com.putoet.day13;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirewallTest {
    private Firewall firewall;

    @BeforeEach
    void setup() {
        final List<String> lines = ResourceLines.list("/day13.txt");
        firewall = Firewall.of(lines);
    }

    @Test
    void of() {
        final List<String> lines = ResourceLines.list("/day13.txt");
        final Firewall firewall = Firewall.of(lines);

        final List<Layer> layers = firewall.layers();
        assertEquals(7, layers.size());
        assertEquals(3, layers.get(0).range());
        assertEquals(2, layers.get(1).range());
        assertEquals(0, layers.get(2).range());
        assertEquals(0, layers.get(3).range());
        assertEquals(4, layers.get(4).range());
        assertEquals(0, layers.get(5).range());
        assertEquals(4, layers.get(6).range());
    }

    @Test
    void layer() {
        assertThrows(AssertionError.class, () -> new Firewall.FirewallLayer(1));

        final Layer layer = new Firewall.FirewallLayer(3);
        assertEquals(0, layer.scanner());
        layer.next();
        assertEquals(1, layer.scanner());
        layer.next();
        assertEquals(2, layer.scanner());
        layer.next();
        assertEquals(1, layer.scanner());
        layer.enter();
        layer.next();
        assertEquals(0, layer.scanner());
        assertFalse(layer.caught());
        layer.leave();;
        layer.next();
        assertEquals(1, layer.scanner());
        layer.next();
        assertEquals(2, layer.scanner());
        layer.next();
        assertEquals(1, layer.scanner());
        layer.next();
        layer.enter();
        assertEquals(0, layer.scanner());
        assertTrue(layer.caught());
    }

    @Test
    void severity() {
        firewall.pass();
        assertEquals(24, firewall.severity());
    }

    @Test
    void caught() {
        long delay = 0;
        do {
            delay++;
            firewall.reset();
            firewall.pass(delay);
        } while (firewall.caught());

        assertEquals(10, delay);
    }
}