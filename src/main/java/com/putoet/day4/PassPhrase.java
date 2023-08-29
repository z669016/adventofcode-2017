package com.putoet.day4;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;

public class PassPhrase {
    public static boolean isValidNoDoubles(@NotNull String phrase) {
        final var words = phrase.split(" ");

        if (words.length < 2)
            return false;

        final var set = new HashSet<String>();
        for (var word : words) {
            if (!word.matches("[a-z]+") || !set.add(word))
                return false;
        }

        return true;
    }

    public static boolean isValidNoAnagrams(@NotNull String phrase) {
        final var words = phrase.split(" ");

        if (words.length < 2)
            return false;

        final var set = new HashSet<String>();
        for (var word : words) {
            final var sorted = word.toCharArray(); Arrays.sort(sorted);
            if (!word.matches("[a-z]+") || !set.add(String.valueOf(sorted)))
                return false;
        }

        return true;
    }
}
