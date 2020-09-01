package com.putoet.day1;

public class IntProducer {
    protected final String lineOfInt;
    protected int idx = 0;

    protected IntProducer(String lineOfInt) {
        this.lineOfInt = lineOfInt;
    }

    public static IntProducer of(String lineOfInt) {
        assert lineOfInt != null;
        assert lineOfInt.matches("[0-9]+");

        return new IntProducer(lineOfInt);
    }

    public void reset() {
        idx = 0;
    }

    public boolean hasNext() {
        return idx < lineOfInt.length();
    }

    public int get() {
        if (idx >= lineOfInt.length())
            throw new IllegalStateException("No values available");

        return lineOfInt.charAt(idx++) - '0';
    }

    public int next() {
        if (idx > lineOfInt.length())
            throw new IllegalStateException("No next values available");

        return offsetChar() - '0';
    }

    protected char offsetChar() {
        return lineOfInt.charAt(idx % lineOfInt.length());
    }
}
