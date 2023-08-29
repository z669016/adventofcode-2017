package com.putoet.day9;

record DataToken(Type type, char data) {
    public enum Type {
        OPEN_GROUP,
        CLOSE_GROUP,
        OPEN_GARBAGE,
        CLOSE_GARBAGE,
        EXCLUDE_NEXT,
        DATA
    }

    public static DataToken of(char c) {
        return switch (c) {
            case '{' -> new DataToken(Type.OPEN_GROUP, c);
            case '}' -> new DataToken(Type.CLOSE_GROUP, c);
            case '<' -> new DataToken(Type.OPEN_GARBAGE, c);
            case '>' -> new DataToken(Type.CLOSE_GARBAGE, c);
            case '!' -> new DataToken(Type.EXCLUDE_NEXT, c);
            default -> new DataToken(Type.DATA, c);
        };
    }
}
