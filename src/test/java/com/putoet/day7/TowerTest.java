package com.putoet.day7;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TowerTest {
    private final Tower tower = Tower.of(ResourceLines.list("/day7.txt"));

    @Test
    void of() {
        assertEquals("tknk", tower.root());
    }

    @Test
    void children() {
        final var children = tower.children("padx");
        assertEquals(List.of("qoyq", "havc", "pbga"), children);
    }

    @Test
    void siblings() {
        final var siblings = tower.siblings("havc");
        assertEquals(List.of("qoyq", "pbga"), siblings);
    }

    @Test
    void weight() {
        assertEquals(68, tower.weight("ugml"));
    }

    @Test
    void totalWeight() {
        assertEquals(251, tower.totalWeight("ugml"));
    }

    @Test
    void unbalancedChild() {
        final var root = tower.root();
        final var unbalancedProgram = tower.unbalancedChild(root);
        if (unbalancedProgram.isPresent()) {
            final var weight = tower.weight(unbalancedProgram.get());
            final var totalWeight = tower.totalWeight(unbalancedProgram.get());
            final var siblings = tower.siblings(unbalancedProgram.get());
            if (!siblings.isEmpty()) {
                assertEquals(60, weight - (totalWeight - tower.totalWeight(siblings.get(0))));
                return;
            }
        }

        fail();
    }
}