package com.putoet.day22;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

class SmarterVirus extends Virus {
    public static final char WEAKENED = 'W';
    public static final char FLAGGED = 'F';

    public SmarterVirus(@NotNull Point start) {
        super(start);
    }

    protected void affectCurrentPosition(char[][] grid) {
        switch (currentNode(grid)) {
            case CLEAN -> weaken(grid);
            case WEAKENED -> infect(grid);
            case INFECTED -> flag(grid);
            case FLAGGED -> clean(grid);
        }
    }

    protected void weaken(char[][] grid) {
        currentNode(grid, WEAKENED);
    }

    protected void flag(char[][] grid) {
        currentNode(grid, FLAGGED);
    }

    protected Direction direction(char state) {
        return switch (state) {
            case CLEAN -> direction().turnLeft();
            case INFECTED -> direction().turnRight();
            case FLAGGED -> direction().reverse();
            default -> direction();
        };
    }
}
