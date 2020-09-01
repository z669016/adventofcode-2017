package com.putoet.day2;

import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day2.txt");
        final Spreadsheet spreadsheet = Spreadsheet.of(lines);

        System.out.println("Checksum is " + spreadsheet.checksum());

        final List<List<Integer>> evenlyDivisableValues = spreadsheet.evenlyDivisableValues();
        final long sum = evenlyDivisableValues.stream()
                .mapToInt(list -> list.get(0) / list.get(1))
                .sum();

        System.out.println("Sum of results is " + sum);
    }
}
