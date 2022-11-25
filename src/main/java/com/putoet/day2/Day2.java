package com.putoet.day2;

import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;

import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day2.txt");
        final Spreadsheet spreadsheet = Spreadsheet.of(lines);

        System.out.println("Checksum is " + spreadsheet.checksum());

        final List<Pair<Integer,Integer>> evenlyDividableValues = spreadsheet.evenlyDividableValues();
        final long sum = evenlyDividableValues.stream()
                .mapToInt(list -> list.getValue0() / list.getValue1())
                .sum();

        System.out.println("Sum of results is " + sum);
    }
}
