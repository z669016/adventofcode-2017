package utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    @Test
    void manhattanDistance() {
        final Point3D somewhere = Point3D.of(1, 3, 7);
        assertEquals(11, somewhere.manhattanDistance(Point3D.ORIGIN));
    }

    @Test
    void add() {
        final Point3D a = Point3D.of(1, -2, -3);
        final Point3D b = Point3D.of(-5, 2, -6);
        final Point3D c = a.add(b);

        assertEquals(Point3D.of(-4, 0, -9), c);
    }

    @Test
    void mul() {
        assertEquals(Point3D.of(3, 9, 21), Point3D.of(1, 3, 7).mul(3));
    }
}