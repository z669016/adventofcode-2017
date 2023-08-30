package com.putoet.day19;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeriesOfTubesTest {

    @Test
    void of() {
        final var seriesOfTubes = SeriesOfTubes.of(ResourceLines.list("/day19.txt"));
        assertEquals("ABCDEF", seriesOfTubes.letters());
        assertEquals(38L, seriesOfTubes.steps());
    }
}