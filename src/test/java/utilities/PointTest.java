package utilities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void manhattanDistance() {
        assertEquals(2, Point.ORIGIN.manhattanDistance(Point.of(1, 1)));
        assertEquals(1, Point.ORIGIN.manhattanDistance(Point.of(0, 1)));
        assertEquals(8, Point.ORIGIN.manhattanDistance(Point.of(-4, -4)));
        assertEquals(6, Point.ORIGIN.manhattanDistance(Point.of(-3, 3)));
    }

    @Test
    void adjacent() {
        assertEquals(List.of(
                Point.of(1, 0),
                Point.of(1, 1),
                Point.of(0, 1),
                Point.of(-1, 1),
                Point.of(-1, 0),
                Point.of(-1, -1),
                Point.of(0, -1),
                Point.of(1, -1)
        ), Point.ORIGIN.adjacend());
    }
}