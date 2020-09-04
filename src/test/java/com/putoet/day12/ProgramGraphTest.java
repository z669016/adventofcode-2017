package com.putoet.day12;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProgramGraphTest {
    private List<String> lines;
    private ProgramGraph graph;

    @BeforeEach
    void setup() {
        lines = ResourceLines.list("/day12.txt");
        graph = ProgramGraph.of(lines);
    }

    @Test
    void group() {
        final Set<Program> group = graph.group(Program.of("0"));
        assertEquals(Set.of(Program.of("0"), Program.of("2"), Program.of("3"), Program.of("4"), Program.of("5"), Program.of("6")), group);
        assertEquals(6, group.size());
        assertFalse(group.contains(Program.of("1")));
    }

    @Test
    void groups() {
        final Set<Set<Program>> groups = graph.groups();
        assertEquals(2, groups.size());
    }
}