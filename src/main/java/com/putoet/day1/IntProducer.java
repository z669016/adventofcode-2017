package com.putoet.day1;

public class IntProducer {
    protected final String lineOfInt;
    protected int idx = 0;
    protected final int offset;

    protected IntProducer(String lineOfInt, int offset) {
        this.lineOfInt = lineOfInt;
        this.offset = offset;
    }

    public static IntProducer of(String lineOfInt) {
        assert lineOfInt != null;
        assert lineOfInt.matches("[0-9]+");

        return new IntProducer(lineOfInt, 0);
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
        return lineOfInt.charAt((idx + offset) % lineOfInt.length());
    }
}
