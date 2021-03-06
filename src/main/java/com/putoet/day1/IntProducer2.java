package com.putoet.day1;

public class IntProducer2 extends IntProducer {
    private final int offset;

    private IntProducer2(String lineOfInt) {
        super(lineOfInt);

        offset = (lineOfInt.length() / 2) - 1;
    }

    public static IntProducer2 of(String lineOfInt) {
        assert lineOfInt != null;
        assert lineOfInt.matches("[0-9]+");

        return new IntProducer2(lineOfInt);
    }

    protected char offsetChar() {
        return lineOfInt.charAt((idx + offset) % lineOfInt.length());
    }
}
