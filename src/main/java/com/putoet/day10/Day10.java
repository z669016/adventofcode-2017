package com.putoet.day10;

import com.putoet.resources.CSV;
import com.putoet.resources.ResourceLines;

import java.util.ArrayList;
import java.util.List;

public class Day10 {
    public static void main(String[] args) {
        final List<Integer> input = CSV.flatList("/day10.txt", Integer::parseInt);

        final KnotHash knotHash = new KnotHash();
        input.forEach(knotHash::reverse);
        System.out.println("Checksum after applying reverse list is: " + knotHash.checksum());

        final List<Integer> sparseHash = sparseHash(input());
        final List<Integer> denseHash = denseHash(sparseHash);
        System.out.println("Hexadecimal of the dense hash of using the input is " + hexadecimal(denseHash));
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

    public static List<Integer> sparseHash(List<Integer> input) {
        final KnotHash knotHash = new KnotHash();
        for (int i = 0; i < 64; i++) {
            input.forEach(knotHash::reverse);
        }

        return knotHash.asList();
    }

    public static List<Integer> denseHash(List<Integer> hash) {
        assert hash.size() % 16 == 0;

        final List<Integer> denseHash = new ArrayList<>();

        for (int block = 0; block < hash.size() / 16; block++) {
            denseHash.add(hash.get(block * 16) ^
                            hash.get(block * 16 + 1) ^
                            hash.get(block * 16 + 2) ^
                            hash.get(block * 16 + 3) ^
                            hash.get(block * 16 + 4) ^
                            hash.get(block * 16 + 5) ^
                            hash.get(block * 16 + 6) ^
                            hash.get(block * 16 + 7) ^
                            hash.get(block * 16 + 8) ^
                            hash.get(block * 16 + 9) ^
                            hash.get(block * 16 + 10) ^
                            hash.get(block * 16 + 11) ^
                            hash.get(block * 16 + 12) ^
                            hash.get(block * 16 + 13) ^
                            hash.get(block * 16 + 14) ^
                            hash.get(block * 16 + 15));
        }

        return denseHash;
    }

    public static String hexadecimal(List<Integer> list) {
        final StringBuffer sb = new StringBuffer();
        list.forEach(i -> sb.append(String.format("%02x", i)));
        return sb.toString();
    }
}
