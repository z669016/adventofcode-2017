package com.putoet.day22;

import com.putoet.grid.Point;
import org.junit.jupiter.api.Test;
import com.putoet.grid.GridUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmarterVirusTest {
    @Test
    void burst() {
        var grid = GridUtils.of(List.of(
                "..#",
                "#..",
                "..."
        ));

        final var virus = new SmarterVirus(Point.of(1, 1));
        for (var i = 0; i < 10_000_000; i++) {
            if (i % 100_000 == 0)
                System.out.print(i / 100_000 + "%\r");

            if (i == 100)
                assertEquals(26, virus.burstInfectedCount());

            grid = virus.burst(grid);
        }
        System.out.println();
        assertEquals(2_511_944, virus.burstInfectedCount());
    }

}