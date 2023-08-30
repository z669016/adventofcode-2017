package com.putoet.day20;

import com.putoet.grid.Point3D;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

record Particle(int id, Point3D position, Point3D velocity, Point3D acceleration) implements Comparable<Particle> {
    public Particle step() {
        return step(1);
    }

    public Particle step(int count) {
        final var updatedPosition = this.position
                .add(this.velocity.transform(p -> p * count))
                .add(this.acceleration.transform(p -> p * IntStream.range(1, count + 1).sum()));

        final var updatedVelocity = this.velocity.add(this.acceleration.transform(p -> p * count));

        return new Particle(id, updatedPosition, updatedVelocity, this.acceleration);
    }

    @Override
    public String toString() {
        return String.format("{id=%d p=%s, v=%s, a=%s}", id, position, velocity, acceleration);
    }

    public static Particle of(int id, @NotNull String line) {
        assert id >= 0;
        assert line.length() >= "p=<0,0,0>, v=<0,0,0>, a=<0,0,0>".length();

        final var pav = line.split(", ");
        assert pav.length == 3;

        return new Particle(id, point3D(pav[0]), point3D(pav[1]), point3D(pav[2]));
    }

    private static Point3D point3D(@NotNull String pointAsString) {
        final var values = pointAsString.substring(3, pointAsString.length() - 1).split(",");
        assert values.length == 3;

        return Point3D.of(
                Integer.parseInt(values[0].trim()),
                Integer.parseInt(values[1].trim()),
                Integer.parseInt(values[2].trim())
        );
    }

    @Override
    public int compareTo(@NotNull Particle other) {
        var compareTo = Integer.compare(acceleration.manhattanDistance(), other.acceleration.manhattanDistance());
        if (compareTo == 0) {
            final var thisDelta =
                    velocity.add(acceleration).add(acceleration).manhattanDistance() -
                            velocity.add(acceleration).manhattanDistance();
            final var otherDelta =
                    other.velocity.add(other.acceleration).add(other.acceleration).manhattanDistance() -
                            other.velocity.add(other.acceleration).manhattanDistance();
            compareTo = Integer.compare(thisDelta, otherDelta);
        }
        if (compareTo == 0)
            compareTo = Integer.compare(position.manhattanDistance(), other.position.manhattanDistance());

        return compareTo;
    }
}
