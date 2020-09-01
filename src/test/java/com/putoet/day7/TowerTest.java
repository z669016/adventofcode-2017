package com.putoet.day7;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TowerTest {
    private final Tower tower = Tower.of(ResourceLines.list("/day7.txt"));

    @Test
    void of() {
        final List<String> roots = tower.roots();
        assertEquals(1, roots.size());
        assertEquals("tknk", roots.get(0));
    }

    @Test
    void children() {
        final List<String> children = tower.children("padx");
        assertEquals(List.of("qoyq", "havc", "pbga"), children);
    }

    @Test
    void siblings() {
        final List<String> siblings = tower.siblings("havc");
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
        int correctWeight = Integer.MIN_VALUE;

        final List<String> roots = tower.roots();
        final Optional<String> unbalancedProgram = tower.unbalancedChild(roots.get(0));
        if (unbalancedProgram.isPresent()) {
            final int weight = tower.weight(unbalancedProgram.get());
            final int totalWeight = tower.totalWeight(unbalancedProgram.get());

            System.out.println("Unbalanced program is " + unbalancedProgram.get() + " (" + weight + ") with total weight " + totalWeight);

            final List<String> siblings = tower.siblings(unbalancedProgram.get());
            if (siblings.size() > 0) {
                final int siblingTotalWeight = tower.totalWeight(siblings.get(0));
                System.out.println("It's siblings have a total weight of " + siblingTotalWeight);

                correctWeight = weight - (totalWeight - siblingTotalWeight);
                System.out.println(unbalancedProgram.get() + " should weigh " + correctWeight);

            }
        }

        assertEquals(60, correctWeight);
    }
}