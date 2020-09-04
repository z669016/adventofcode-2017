package com.putoet.day13;

import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day13 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day13.txt");
        Firewall firewall = Firewall.of(lines);

        firewall.pass();
        System.out.println("Severity is " + firewall.severity());

        long delay = 0;
        do {
            delay++;

            if (delay == 0) {
                throw new IllegalArgumentException("counter overflow error !!!");
            }

            firewall.reset();
            firewall.pass(delay);
        } while (firewall.caught());

        System.out.println();
        System.out.println("You can pass without being caught with a delay of " + delay);
    }
}
