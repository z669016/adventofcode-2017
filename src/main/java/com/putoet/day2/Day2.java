package com.putoet.day2;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;
import org.javatuples.Pair;

import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        final var lines = ResourceLines.list("/day2.txt");
        final var spreadsheet = Spreadsheet.of(lines);

        Timer.run(() -> System.out.println("Checksum is " + spreadsheet.checksum()));

        Timer.run(() -> {
            final var evenlyDividableValues = spreadsheet.evenlyDividableValues();
            final var sum = evenlyDividableValues.stream()
                    .mapToInt(list -> list.getValue0() / list.getValue1())
                    .sum();

            System.out.println("Sum of results is " + sum);
        });
    }
}
