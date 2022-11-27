package com.putoet.day11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HexGridTest {
    @BeforeEach
    void setup() {
        HexGrid.reset();
    }

    @Test
    void reset() {
        final HexGrid origin = HexGrid.origin();
        origin.move(Direction.NORTH).move(Direction.NORTH);

        assertNotEquals(HexGrid.origin(), HexGrid.max());
        HexGrid.reset();

        assertEquals(HexGrid.origin(), HexGrid.max());
    }

    @Test
    void move() {
        final HexGrid north = HexGrid.origin().move(Direction.NORTH);
        assertEquals(north.x(), HexGrid.origin().x());
        assertEquals(north.y(), HexGrid.origin().y() + 2);

        final HexGrid northEast = HexGrid.origin().move(Direction.NORTH_EAST);
        assertEquals(northEast.x(), HexGrid.origin().x() + 1);
        assertEquals(northEast.y(), HexGrid.origin().y() + 1);

        final HexGrid southEast = HexGrid.origin().move(Direction.SOUTH_EAST);
        assertEquals(southEast.x(), HexGrid.origin().x() + 1);
        assertEquals(southEast.y(), HexGrid.origin().y() - 1);

        final HexGrid south = HexGrid.origin().move(Direction.SOUTH);
        assertEquals(south.x(), HexGrid.origin().x());
        assertEquals(south.y(), HexGrid.origin().y() - 2);

        final HexGrid southWest = HexGrid.origin().move(Direction.SOUTH_WEST);
        assertEquals(southWest.x(), HexGrid.origin().x() - 1);
        assertEquals(southWest.y(), HexGrid.origin().y() - 1);

        final HexGrid northWest = HexGrid.origin().move(Direction.NORTH_WEST);
        assertEquals(northWest.x(), HexGrid.origin().x() - 1);
        assertEquals(northWest.y(), HexGrid.origin().y() + 1);
    }

    @Test
    void distance() {
        HexGrid grid = make("ne,ne,ne");
        assertEquals(3, HexGrid.origin().distance(grid));

        grid = make("ne,ne,sw,sw");
        assertEquals(0, HexGrid.origin().distance(grid));

        grid = make("ne,ne,s,s");
        assertEquals(2, HexGrid.origin().distance(grid));

        grid = make("se,sw,se,sw,sw");
        assertEquals(3, HexGrid.origin().distance(grid));
    }

    private HexGrid make(String list) {
        final List<Direction> directions = Arrays.stream(list.split(","))
                .map(Direction::of)
                .collect(Collectors.toList());

        HexGrid.reset();
        return HexGrid.origin().move(directions);
    }
}