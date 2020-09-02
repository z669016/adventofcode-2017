package com.putoet.day9;

public class GroupData {
    public static final char OPEN_GROUP = '{';
    public static final char CLOSE_GROUP = '}';
    public static final char OPEN_GARBAGE = '<';
    public static final char CLOSE_GARBAGE = '>';
    public static final char COMMA = ',';
    public static final char EXCLUDE_NEXT = '!';
    public static final char EOF = '\0';

    private final char[] data;
    private int offset = 0;

    public GroupData(String data) {
        this.data = data.toCharArray();
    }

    public char get() {
        return (offset < data.length ? data[offset++] : EOF);
    }

    public void unget() {
        offset--;
    }
}
