package com.putoet.day16;

import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.ArrayList;

public class Day16 {
    public static final int REPEATS = 1_000_000_000;

    public static void main(String[] args) {
        final var moves = CSV.flatList("/day16.txt");
        final var danceLine = new DanceLine("abcdefghijklmnop");
        final var history = new ArrayList<String>();

        Timer.run(() -> {
            history.add(danceLine.toString());
            danceLine.dance(moves);
            history.add(danceLine.toString());
            System.out.println("Order after the dance is " + danceLine);
        });

        Timer.run(() -> {
            for (var idx = 1; idx < REPEATS; idx++) {
                danceLine.dance(moves);
                if (history.contains(danceLine.toString())) {
                    break;
                }

                history.add(danceLine.toString());
            }

            System.out.println("Order after many, many dances is " + history.get(REPEATS % history.size()));
        });
    }
}
