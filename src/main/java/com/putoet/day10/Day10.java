package com.putoet.day10;

import com.putoet.resources.CSV;
import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day10 {
    public static void main(String[] args) {
        final var input = CSV.flatList("/day10.txt", Integer::parseInt);

        Timer.run(() -> {
            final var knotHash = new KnotHash();
            input.forEach(knotHash::reverse);
            System.out.println("Checksum after applying reverse list is: " + knotHash.checksum());
        });

        Timer.run(() -> {
            final var line = ResourceLines.line("/day10.txt");
            System.out.println("Hexadecimal of the dense hash of using the input is " + KnotHash.hash(KnotHash.createKey(line)));
        });
    }
}
