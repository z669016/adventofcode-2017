package com.putoet.day24;

import com.putoet.resources.ResourceLines;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day24 {
    public static void main(String[] args) {
        final List<Bridge> bridges = bridges();

        System.out.println("From the list, you can create " + bridges.size() + " different bridges.");
        int strongest = bridges.stream().mapToInt(Bridge::strength).max().orElseThrow();
        System.out.println("The strongest bridge has strength " + strongest);

        final int longest = bridges.stream().mapToInt(Bridge::length).max().orElseThrow();
        strongest = bridges.stream()
                .filter(bridge -> bridge.length() == longest)
                .mapToInt(Bridge::strength)
                .max()
                .orElseThrow();

        System.out.println("The strongest longest bridge is " + strongest);
    }

    public static List<Bridge> bridges() {
        final Components components = Components.of(ResourceLines.list("/day24.txt"));
        final List<Component> starters = components.forPort(0);

        final Queue<Bridge> queue = new LinkedList<>();
        starters.forEach(starter -> queue.offer(new Bridge(starter, components)));

        final List<Bridge> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            final Bridge bridge = queue.poll();

            final List<Bridge> options = bridge.options();
            if (options.size() == 0)
                result.add(bridge);
            else
                options.forEach(queue::offer);
        }
        return result;
    }
}
