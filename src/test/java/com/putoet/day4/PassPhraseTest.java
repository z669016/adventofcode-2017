package com.putoet.day4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassPhraseTest {

    @Test
    void isValid() {
        assertFalse(PassPhrase.isValidNoDoubles("aa bb cc d1 ee"));
        assertFalse(PassPhrase.isValidNoDoubles("aa bb c-c dd ee"));

        assertTrue(PassPhrase.isValidNoDoubles("aa bb cc dd ee"));
        assertFalse(PassPhrase.isValidNoDoubles("aa bb cc dd aa"));
        assertTrue(PassPhrase.isValidNoDoubles("aa bb cc dd aaa"));
    }
}