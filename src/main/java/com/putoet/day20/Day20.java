package com.putoet.day20;

import com.putoet.resources.ResourceLines;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

public class Day20 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day20.txt");
        final List<Particle> particles = IntStream.range(0, lines.size())
                .mapToObj(i -> Particle.of(i, lines.get(i)))
                .sorted(Comparator.naturalOrder())
                .toList();

        System.out.println("Closest particle (on the long run) will me " + particles.get(0));

        final List<Particle> distinct = removeColliding(particles);
        System.out.println("There are " + distinct.size() + " non-colliding particles");
    }

    public static List<Particle> removeColliding(List<Particle> particles) {
        int size = particles.size();
        int unchanged = 0;
        List<Particle> next = particles;

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
