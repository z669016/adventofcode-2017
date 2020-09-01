package com.putoet.day4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PassPhrase {
    public static boolean isValidNoDoubles(String phrase) {
        assert phrase != null;
        final String[] words = phrase.split(" ");

        if (words.length < 2)
            return false;

        final Set<String> set = new HashSet<>();
        for (String word : words) {
            if (!word.matches("[a-z]+") || !set.add(word))
                return false;
        }

        return true;
    }

    public static boolean isValidNoAnnagrams(String phrase) {
        assert phrase != null;
        final String[] words = phrase.split(" ");

        if (words.length < 2)
            return false;

        final Set<String> set = new HashSet<>();
        for (String word : words) {
            char[] sorted = word.toCharArray(); Arrays.sort(sorted);
            if (!word.matches("[a-z]+") || !set.add(String.valueOf(sorted)))
                return false;
        }

        return true;
    }
}
