package com.putoet.day19;

import utilities.Point;

import java.util.List;

public class Tube {
    public final Point start, end;
    public final List<Character> letters;

    public Tube(Point start, Point end, List<Character> letters) {
        this.start = start;
        this.end = end;
        this.letters = letters;
    }

    public long steps() {
        return Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
    }

    @Override
    public String toString() {
        return start + " -> " + end + (letters.size() > 0 ? " " + letters : "");
    }
}
