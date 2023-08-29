package com.putoet.day12;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day12 {
    public static void main(String[] args) {
        final ProgramGraph graph = ProgramGraph.of(ResourceLines.list("/day12.txt"));

        Timer.run(() ->
                System.out.println("Group containing program 0 has size " + graph.group(new Program("0")).size())
        );

        Timer.run(() -> System.out.println("The graph contains " + graph.groups().size() + " groups."));
    }
}
