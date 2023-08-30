package com.putoet.day24;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day24 {
    public static void main(String[] args) {
        final var bridges = bridges();
        System.out.println("From the list, you can create " + bridges.size() + " different bridges.");

        Timer.run(() -> {
            final var strongest = bridges.stream().mapToInt(Bridge::strength).max().orElseThrow();
            System.out.println("The strongest bridge has strength " + strongest);
        });

        Timer.run(() -> {
            final var longest = bridges.stream().mapToInt(Bridge::length).max().orElseThrow();
            final var strongest = bridges.stream()
                    .filter(bridge -> bridge.length() == longest)
                    .mapToInt(Bridge::strength)
                    .max()
                    .orElseThrow();

            System.out.println("The strongest longest bridge is " + strongest);
        });
    }

    static List<Bridge> bridges() {
        final var components = Components.of(ResourceLines.list("/day24.txt"));
        final var starters = components.forPort(0);

        final var queue = new LinkedList<Bridge>();
        starters.forEach(starter -> queue.offer(new Bridge(starter, components)));

        final var result = new ArrayList<Bridge>();
        while (!queue.isEmpty()) {
            final var bridge = queue.poll();
            final var options = bridge.options();
            if (options.isEmpty())
                result.add(bridge);
            else
                options.forEach(queue::offer);
        }
        return result;
    }
}
