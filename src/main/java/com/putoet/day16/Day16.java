package com.putoet.day16;

import com.putoet.resources.CSV;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Day16 {
    public static void main(String[] args) {
        final List<String> moves = CSV.flatList("/day16.txt");
        final DanceLine danceLine = new DanceLine("abcdefghijklmnop");

        final List<String> prev = new ArrayList<>();
        prev.add(danceLine.toString());

        danceLine.dance(moves);
        prev.add(danceLine.toString());
        System.out.println("Order after the dance is " + danceLine.toString());

        for (int idx = 1; idx < 1_000_000_000; idx++) {
            danceLine.dance(moves);
            if (prev.contains(danceLine.toString())) {
                System.out.println("Found repetition after " + idx + " iterations");
                break;
            }

            prev.add(danceLine.toString());
        }

        System.out.println("Order after 1.000.000.000 dances is " + prev.get(1_000_000_000 % prev.size()));
    }
}
