package com.putoet.day12;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProgramGraphTest {
    private ProgramGraph graph;

    @BeforeEach
    void setup() {
        final var lines = ResourceLines.list("/day12.txt");
        graph = ProgramGraph.of(lines);
    }

    @Test
    void group() {
        final var group = graph.group(new Program("0"));
        assertEquals(Set.of(
                new Program("0"),
                new Program("2"),
                new Program("3"),
                new Program("4"),
                new Program("5"),
                new Program("6")
        ), group);
        assertEquals(6, group.size());
        assertFalse(group.contains(new Program("1")));
    }

    @Test
    void groups() {
        final var groups = graph.groups();
        assertEquals(2, groups.size());
    }
}