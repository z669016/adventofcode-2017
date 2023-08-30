package com.putoet.day20;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

public class Day20 {
    public static void main(String[] args) {
        final var particles = Timer.run(() -> {
            final var lines = ResourceLines.list("/day20.txt");
            var list = IntStream.range(0, lines.size())
                    .mapToObj(i -> Particle.of(i, lines.get(i)))
                    .sorted(Comparator.naturalOrder())
                    .toList();

            System.out.println("Closest particle (on the long run) will be " + list.get(0));
            return list;
        });

        Timer.run(() -> {
            final List<Particle> distinct = removeColliding(particles);
            System.out.println("There are " + distinct.size() + " non-colliding particles");
        });
    }

    public static List<Particle> removeColliding(List<Particle> particles) {
        var size = particles.size();
        var unchanged = 0;
        var next = particles;

        while (unchanged < 1000) {
            next = next.stream()
                    .map(Particle::step)
                    .collect(groupingBy(Particle::position)).values().stream()
                    .filter(l -> l.size() == 1)
                    .flatMap(List::stream)
                    .toList();

            if (next.size() == size) {
                unchanged++;
            } else {
                unchanged = 0;
                size = next.size();
            }
        }

        return next;
    }
}
