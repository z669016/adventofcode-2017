package com.putoet.day4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassPhraseTest {

    @Test
    void isValidNoDubles() {
        assertFalse(PassPhrase.isValidNoDoubles("aa bb cc d1 ee"));
        assertFalse(PassPhrase.isValidNoDoubles("aa bb c-c dd ee"));

        assertTrue(PassPhrase.isValidNoDoubles("aa bb cc dd ee"));
        assertFalse(PassPhrase.isValidNoDoubles("aa bb cc dd aa"));
        assertTrue(PassPhrase.isValidNoDoubles("aa bb cc dd aaa"));
    }

    @Test
    void isValidNoAnagrams() {
        assertTrue(PassPhrase.isValidNoAnagrams("abcde fghij"));
        assertFalse(PassPhrase.isValidNoAnagrams("abcde xyz ecdab"));
        assertTrue(PassPhrase.isValidNoAnagrams("a ab abc abd abf abj"));
        assertTrue(PassPhrase.isValidNoAnagrams("iii oiii ooii oooi oooo"));
        assertFalse(PassPhrase.isValidNoAnagrams("oiii ioii iioi iiio"));
    }
}