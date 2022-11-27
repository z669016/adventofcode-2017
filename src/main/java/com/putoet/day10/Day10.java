package com.putoet.day10;

import com.putoet.resources.CSV;
import com.putoet.resources.ResourceLines;
import utilities.KnotHash;

import java.util.List;

public class Day10 {
    public static void main(String[] args) {
        final KnotHash knotHash = new KnotHash();
        final List<Integer> input = CSV.flatList("/day10.txt", Integer::parseInt);
        input.forEach(knotHash::reverse);
        System.out.println("Checksum after applying reverse list is: " + knotHash.checksum());

        final String line = ResourceLines.line("/day10.txt");
        System.out.println("Hexadecimal of the dense hash of using the input is " + KnotHash.hash(KnotHash.createKey(line)));
    }
}
