package com.putoet.day20;

import utilities.Point3D;

import java.util.Objects;
import java.util.stream.IntStream;

public class Particle implements Comparable<Particle> {
    private final int id;
    private final Point3D position;
    private final Point3D velocity;
    private final Point3D acceleration;

    private Particle(int id, Point3D position, Point3D velocity, Point3D acceleration) {
        this.id = id;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public int id(){ return id; }
    public Point3D position() {
        return position;
    }
    public Point3D acceleration() {
        return acceleration;
    }
    public Point3D velocity() {
        return velocity;
    }

    public Particle step() {
        return step(1);
    }

    public Particle step(int count) {
        final Point3D acceleration = this.acceleration;
        final Point3D velocity = this.velocity.add(acceleration.mul(count));
        final Point3D position = this.position.add(this.velocity.mul(count)).add(acceleration.mul(IntStream.range(1,count+1).sum()));

        return new Particle(id, position, velocity, acceleration);
    }

    @Override
    public String toString() {
        return String.format("{id=%d p=%s, v=%s, a=%s}", id, position, velocity, acceleration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return id == particle.id &&
                position.equals(particle.position) &&
                velocity.equals(particle.velocity) &&
                acceleration.equals(particle.acceleration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, velocity, acceleration);
    }

    public static Particle of(int id, String line) {
        assert id >= 0;
        assert line != null && line.length() >= "p=<0,0,0>, v=<0,0,0>, a=<0,0,0>".length();
        final String[] pav = line.split(", ");
        assert pav.length == 3;

        return new Particle(id, point3D(pav[0]), point3D(pav[1]), point3D(pav[2]));
    }

    private static Point3D point3D(String pav) {
        assert pav != null;
        final String[] values = pav.substring(3, pav.length() - 1).split(",");
        assert values.length == 3;

        return Point3D.of(Integer.parseInt(values[0].trim())
                , Integer.parseInt(values[1].trim())
                , Integer.parseInt(values[2].trim()));
    }

    @Override
    public int compareTo(Particle other) {

        int compareTo = acceleration.compareTo(other.acceleration);
        if (compareTo == 0) {
            final int thisDelta =
                    velocity.add(acceleration).add(acceleration).manhattanDistance() -
                            velocity.add(acceleration).manhattanDistance();
            final int otherDelta =
                    other.velocity.add(other.acceleration).add(other.acceleration).manhattanDistance() -
                            other.velocity.add(other.acceleration).manhattanDistance();
            compareTo = Integer.compare(thisDelta, otherDelta);
        }
        if (compareTo == 0)
            compareTo = Integer.compare(position.manhattanDistance(), other.position.manhattanDistance());

        return compareTo;
    }
}
