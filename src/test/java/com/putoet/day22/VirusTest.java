package com.putoet.day22;

import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;
import com.putoet.grid.GridUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VirusTest {

    @Test
    void burst() {
        var grid = GridUtils.of(List.of(
                "..#",
                "#..",
                "..."
        ));

        final var virus = new Virus(Point.of(1, 1));
        for (var i = 0; i < 10_000; i++) {
            if (i == 7)
                assertEquals(5, virus.burstInfectedCount());
            if (i == 70)
                assertEquals(41, virus.burstInfectedCount());

            grid = virus.burst(grid);
        }
        assertEquals(5587, virus.burstInfectedCount());
    }
}