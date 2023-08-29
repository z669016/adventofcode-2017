package com.putoet.day9;

import java.util.Iterator;

class DataTokenizer implements Iterator<DataToken> {
    private final char[] data;
    private int offset = 0;

    public DataTokenizer(String data) {
        this.data = data.toCharArray();
    }

    @Override
    public boolean hasNext() {
        return offset < data.length;
    }

    @Override
    public DataToken next() {
        if (offset >= data.length)
            throw new IllegalStateException();

        return DataToken.of(data[offset++]);
    }
}
