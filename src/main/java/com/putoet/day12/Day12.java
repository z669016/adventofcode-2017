package com.putoet.day12;

import com.putoet.resources.ResourceLines;

import java.util.List;
import java.util.Set;

public class Day12 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day12.txt");
        final ProgramGraph graph = ProgramGraph.of(lines);

        final Set<Program> group = graph.group(new Program("0"));
        System.out.println("Group containing program 0 has size " + group.size());
        System.out.println("The graph contains " + graph.groups().size() + " groups.");
    }
}
