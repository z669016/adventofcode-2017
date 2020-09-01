package com.putoet.day4;

import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day4 {
    public static void main(String[] args) {
        final List<String> phrases = ResourceLines.list("/day4.txt");

        long valid = phrases.stream().filter(PassPhrase::isValidNoDoubles).count();
        System.out.println("Number of valid pass phrases (no doubles) is " + valid);

        valid = phrases.stream().filter(PassPhrase::isValidNoAnnagrams).count();
        System.out.println("Number of valid pass phrases (no anagrams) is " + valid);
    }
}
