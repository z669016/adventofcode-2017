package com.putoet.day20;

import com.putoet.grid.Point3D;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ParticleTest {

    @Test
    void of() {
        final var particle = Particle.of(3, "p=<-1027,-979,-188>, v=<7,60,66>, a=<9,1,-7>");
        assertEquals(3, particle.id());
        assertEquals(Point3D.of(-1027,-979,-188), particle.position());
        assertEquals(Point3D.of(7, 60, 66), particle.velocity());
        assertEquals(Point3D.of(9, 1, -7), particle.acceleration());
    }

    @Test
    void sort() {
        final var lines = ResourceLines.list("/day20.txt");
        final var particles = IntStream.range(0, lines.size())
                .mapToObj(i -> Particle.of(i, lines.get(i)))
                .sorted(Comparator.naturalOrder())
                .toList();

        assertEquals(0, particles.get(0).id());
    }

    @Test
    void step() {
        final var particle = Particle.of(3, "p=<0,0,0>, v=<1,2,3>, a=<4,5,6>");
        final var stepOne = Particle.of(3, "p=<5,7,9>, v=<5,7,9>, a=<4,5,6>");
        final var stepTwo = Particle.of(3, "p=<14,19,24>, v=<9,12,15>, a=<4,5,6>");
        final var stepThree = Particle.of(3, "p=<27,36,45>, v=<13,17,21>, a=<4,5,6>");
        final var stepFour = Particle.of(3, "p=<44,58,72>, v=<17,22,27>, a=<4,5,6>");

        assertEquals(stepOne, particle.step());
        assertEquals(stepTwo, particle.step().step());
        assertEquals(stepThree, particle.step().step().step());
        assertEquals(stepFour, particle.step().step().step().step());

        assertEquals(stepThree, particle.step(3));
        assertEquals(stepFour, particle.step(4));
    }
}