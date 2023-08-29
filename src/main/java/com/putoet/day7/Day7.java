package com.putoet.day7;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day7 {
    public static void main(String[] args) {
        final var tower = Timer.run(() -> {
            final var t = Tower.of(ResourceLines.list("/day7.txt"));
            final var root = t.root();
            System.out.println("Roots found: " + root);
            return t;
        });

        Timer.run(() -> {
            final var unbalancedProgram = tower.unbalancedChild(tower.root()).orElseThrow();
            final var weight = tower.weight(unbalancedProgram);
            final var totalWeight = tower.totalWeight(unbalancedProgram);

            System.out.println("Unbalanced program is " + unbalancedProgram + " (" + weight + ") with total weight " + totalWeight);

            final var siblings = tower.siblings(unbalancedProgram);
            if (!siblings.isEmpty()) {
                final var siblingTotalWeight = tower.totalWeight(siblings.get(0));
                System.out.println("It's siblings have a total weight of " + siblingTotalWeight);
                System.out.println(unbalancedProgram + " should weigh " + (weight - (totalWeight - siblingTotalWeight)));
            }
        });
    }
}
