package com.putoet.day12;

import com.putoet.graph.UnweightedGraph;

import java.util.*;

public class ProgramGraph extends UnweightedGraph<Program> {
    public static ProgramGraph of(List<String> lines) {
        final ProgramGraph graph = new ProgramGraph();
        lines.forEach(line -> {
            final Program program = program(line);
            if (!graph.contains(program))
                graph.addVertex(program);

            final List<Program> connects = connects(line);
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
        assert line != null;

        return new Program(defSplit(line)[0]);
    }

    private static String[] defSplit(String line) {
        final String[] parts = line.split(" <-> ");
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
        final Set<Program> visited = new HashSet<>();
        final Set<Program> group = new HashSet<>();

        final Queue<Program> queue = new LinkedList<>();
        queue.offer(program);

        while(!queue.isEmpty()) {
            final Program p = queue.poll();
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
        final Set<Program> visited = new HashSet<>();
        final Set<Set<Program>> groups = new HashSet<>();

        for (Program program : vertices) {
            if (!visited.contains(program)) {
                final Set<Program> group = group(program);
                visited.addAll(group);

                groups.add(group);
            }
        }

        return groups;
    }
}
