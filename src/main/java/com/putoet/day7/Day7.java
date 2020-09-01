package com.putoet.day7;

import com.putoet.resources.ResourceLines;

import java.util.List;
import java.util.Optional;

public class Day7 {
    public static void main(String[] args) {
        final Tower tower = Tower.of(ResourceLines.list("/day7.txt"));
        final List<String> roots = tower.roots();

        System.out.println("Roots found: " + roots);

        final Optional<String> unbalancedProgram = tower.unbalancedChild(roots.get(0));
        if (unbalancedProgram.isPresent()) {
            final int weight = tower.weight(unbalancedProgram.get());
            final int totalWeight = tower.totalWeight(unbalancedProgram.get());

            System.out.println("Unbalanced program is " + unbalancedProgram.get() + " (" + weight + ") with total weight " + totalWeight);

            final List<String> siblings = tower.siblings(unbalancedProgram.get());
            if (siblings.size() > 0) {
                final int siblingTotalWeight = tower.totalWeight(siblings.get(0));
                System.out.println("It's siblings have a total weight of " + siblingTotalWeight);

                System.out.println(unbalancedProgram.get() + " should weigh " + (weight - (totalWeight - siblingTotalWeight)));
            }
        }
    }
}
