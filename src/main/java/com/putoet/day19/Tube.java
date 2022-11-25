package com.putoet.day19;

import com.putoet.grid.Point;

import java.util.List;

public record Tube(Point start, Point end, List<Character> letters) {

    public long steps() {
        return Math.abs(start.x() - end.x()) + Math.abs(start.y() - end.y());
    }

    @Override
    public String toString() {
        return start + " -> " + end + (letters.size() > 0 ? " " + letters : "");
    }
}
