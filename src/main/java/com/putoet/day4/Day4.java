package com.putoet.day4;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day4 {
    public static void main(String[] args) {
        final var phrases = ResourceLines.list("/day4.txt");

        Timer.run(() -> {
            final var valid = phrases.stream().filter(PassPhrase::isValidNoDoubles).count();
            System.out.println("Number of valid pass phrases (no doubles) is " + valid);
        });

        Timer.run(() -> {
            final var valid = phrases.stream().filter(PassPhrase::isValidNoAnagrams).count();
            System.out.println("Number of valid pass phrases (no anagrams) is " + valid);
        });
    }
}
