package com.putoet.day1;

import org.jetbrains.annotations.NotNull;

class IntProducer2 extends IntProducer {

    private IntProducer2(@NotNull String lineOfInt) {
        super(lineOfInt, (lineOfInt.length() / 2) - 1);
    }

    public static IntProducer2 of(@NotNull String lineOfInt) {
        assert lineOfInt.matches("[0-9]+");

        return new IntProducer2(lineOfInt);
    }
}