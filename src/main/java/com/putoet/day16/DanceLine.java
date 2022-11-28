package com.putoet.day16;

import java.util.Arrays;
import java.util.List;

public class DanceLine {
    private final char[] line;

    public DanceLine(String line) {
        assert line != null && line.length() > 0;
        this.line = line.toCharArray();
    }

    public void dance(List<String> moves) {
        assert moves != null;

        moves.forEach(move -> {
            switch (move.charAt(0)) {
                case 's' -> spin(moveSize(move));
                case 'x' -> exchange(movePos(move, 1), movePos(move, 2));
                case 'p' -> partner(moveChar(move, 1), moveChar(move, 2));
            }
        });
    }

    private int movePos(String move, int p) {
        assert p == 1 || p == 2;

        return switch (p) {
            case 1 -> Integer.parseInt(move.substring(1, move.indexOf("/")));
            case 2 -> Integer.parseInt(move.substring(move.indexOf("/") + 1));
            default -> throw new IllegalArgumentException("Invalid position " + p);
        };
    }

    private char moveChar(String move, int p) {
        assert p == 1 || p == 2;

        return switch (p) {
            case 1 -> move.charAt(1);
            case 2 -> move.charAt(3);
            default -> throw new IllegalArgumentException("Invalid position " + p);
        };
    }

    private static int moveSize(String move) {
        return Integer.parseInt(move.substring(1));
    }

    public DanceLine spin(int size) {
        assert size >= 0 && size <= line.length;

        if (size != 0)
            spin(line, size);

        return this;
    }

    public DanceLine exchange(int a, int b) {
        assert a >= 0 && a < line.length;
        assert b >= 0 && b < line.length;

        if (a != b)
            exchange(line, a, b);

        return this;
    }

    public DanceLine partner(char a, char b) {
        final int pA = indexOf(line, a);
        final int pB = indexOf(line, b);

        assert pA != -1;
        assert pB != -1;

        exchange(line, pA, pB);

        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(line);
    }

    private static void spin(char[] line, int size) {
        final char[] temp = Arrays.copyOfRange(line, line.length - size, line.length);

        for (int idx = line.length - 1; idx >= size; idx--)
            line[idx] = line[idx - size];
        if (size >= 0) System.arraycopy(temp, 0, line, 0, size);
    }

    private static void exchange(char[] line, int a, int b) {
        final char temp = line[a];
        line[a] = line[b];
        line[b] = temp;
    }

    private static int indexOf(char[] line, char c) {
        for (int idx = 0; idx < line.length; idx++)
            if (line[idx] == c) return idx;

        return -1;
    }
}
