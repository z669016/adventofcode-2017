package utilities;

import java.util.Objects;

public class Point3D implements Comparable<Point3D> {
    public static final Point3D ORIGIN = Point3D.of(0,0,0);
    public final int x, y, z;

    private Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int manhattanDistance() {
        return manhattanDistance(ORIGIN);
    }

    public int manhattanDistance(Point3D other) {
        return Math.abs(x-other.x) + Math.abs(y-other.y)+Math.abs(z-other.z);
    }

    public Point3D mul(int count) {
        return new Point3D(x * count, y * count, z * count);
    }

    public Point3D add(Point3D other) {
        return add(this, other);
    }

    public static Point3D add(Point3D a, Point3D b) {
        return Point3D.of(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;
        Point3D point3D = (Point3D) o;
        return x == point3D.x &&
                y == point3D.y &&
                z == point3D.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "("+x+","+y+","+z+")";
    }

    public static Point3D of(int x, int y, int z) {
        return new Point3D(x, y, z);
    }

    @Override
    public int compareTo(Point3D other) {
        assert other != null;

        return Integer.compare(manhattanDistance(), other.manhattanDistance());
    }
}
