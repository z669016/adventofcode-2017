package com.putoet.day10;

import com.putoet.resources.CSV;
import com.putoet.resources.ResourceLines;
import utilities.KnotHash;

import java.util.ArrayList;
import java.util.List;

public class Day10 {
    public static void main(String[] args) {
        final List<Integer> input = CSV.flatList("/day10.txt", Integer::parseInt);

        final KnotHash knotHash = new KnotHash();
        input.forEach(knotHash::reverse);
        System.out.println("Checksum after applying reverse list is: " + knotHash.checksum());
        System.out.println("Hexadecimal of the dense hash of using the input is " + KnotHash.hash(input()));
    }

    public static List<Integer> input() {
        final List<String> lines = ResourceLines.list("/day10.txt");
        return input(lines.get(0));
    }

    public static List<Integer> input(String line) {
        assert line != null;

        final List<Integer> list = new ArrayList<>();
        line.chars().forEach(list::add);
        list.addAll(List.of(17, 31, 73, 47, 23));

        return list;
    }
}
