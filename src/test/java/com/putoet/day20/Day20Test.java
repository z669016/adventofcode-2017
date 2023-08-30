package com.putoet.day20;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20Test {

    @Test
    void removeColliding() {
        final var lines = List.of(
                "p=<-6,0,0>, v=<3,0,0>, a=<0,0,0>",
                "p=<-4,0,0>, v=<2,0,0>, a=<0,0,0>",
                "p=<-2,0,0>, v=<1,0,0>, a=<0,0,0>",
                "p=< 3,0,0>, v=<-1,0,0>, a=<0,0,0>"
        );
        var particles = IntStream.range(0, lines.size())
                .mapToObj(i -> Particle.of(i, lines.get(i)))
                .sorted(Comparator.naturalOrder())
                .toList();

        particles = Day20.removeColliding(particles);
        assertEquals(1, particles.size());
        assertEquals(3, particles.get(0).id());
    }
}