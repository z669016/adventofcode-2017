package com.putoet.day12;

import com.putoet.graph.UnweightedGraph;

import java.util.*;

class ProgramGraph extends UnweightedGraph<Program> {
    public static ProgramGraph of(List<String> lines) {
        final var graph = new ProgramGraph();
        lines.forEach(line -> {
            final var program = program(line);
            if (!graph.contains(program))
                graph.addVertex(program);

            final var connects = connects(line);
            connects.forEach(connecting -> {
                if (!graph.contains(connecting))
                    graph.addVertex(connecting);

                if (!graph.neighboursOf(program).contains(connecting))
                    graph.addEdge(program, connecting);
            });
        });

        return graph;
    }

    private static Program program(String line) {
        return new Program(defSplit(line)[0]);
    }

    private static String[] defSplit(String line) {
        final var parts = line.split(" <-> ");
        if (parts.length != 2)
            throw new IllegalArgumentException("Invalid program dependency line '" + line + "'");

        return parts;
    }

    private static List<Program> connects(String line) {
        return Arrays.stream(defSplit(line)[1].split(", "))
                .map(Program::new)
                .toList();
    }

    public ProgramGraph() {
        super();
    }

    public Set<Program> group(Program program) {
        final var visited = new HashSet<Program>();
        final var group = new HashSet<Program>();

        final var queue = new LinkedList<Program>();
        queue.offer(program);

        while(!queue.isEmpty()) {
            final var p = queue.poll();
            group.add(p);
            visited.add(p);

            neighboursOf(p).forEach(c -> {
                if (!visited.contains(c))
                    queue.offer(c);
            });
        }

        return group;
    }

    public Set<Set<Program>> groups() {
        final var visited = new HashSet<Program>();
        final var groups = new HashSet<Set<Program>>();

        for (var program : vertices) {
            if (!visited.contains(program)) {
                final var group = group(program);
                visited.addAll(group);
                groups.add(group);
            }
        }

        return groups;
    }
}
