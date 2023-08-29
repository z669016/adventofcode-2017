package com.putoet.day13;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;

public class Day13 {
    public static void main(String[] args) {
        final Firewall firewall = Firewall.of(ResourceLines.list("/day13.txt"));

        Timer.run(() -> {
            firewall.pass();
            System.out.println("Severity is " + firewall.severity());
        });

        Timer.run(() -> {
            long delay = 0;
            do {
                delay++;
                firewall.reset();
                firewall.pass(delay);
            } while (firewall.caught());

            System.out.println("You can pass without being caught with a delay of " + delay);
        });
    }
}
