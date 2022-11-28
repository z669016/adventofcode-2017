package com.putoet.day16;

import com.putoet.resources.CSV;

import java.util.ArrayList;
import java.util.List;

public class Day16 {

    public static final int REPEATS = 1_000_000_000;

    public static void main(String[] args) {
        final List<String> moves = CSV.flatList("/day16.txt");
        final DanceLine danceLine = new DanceLine("abcdefghijklmnop");

        final List<String> history = new ArrayList<>();
        history.add(danceLine.toString());
        danceLine.dance(moves);
        history.add(danceLine.toString());
        System.out.println("Order after the dance is " + danceLine);

        for (int idx = 1; idx < REPEATS; idx++) {
            danceLine.dance(moves);
            if (history.contains(danceLine.toString())) {
                System.out.println("Found repetition after " + idx + " iterations");
                break;
            }

            history.add(danceLine.toString());
        }

        System.out.println("Order after many, many dances is " + history.get(REPEATS % history.size()));
    }
}
