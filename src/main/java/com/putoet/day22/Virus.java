package com.putoet.day22;

import com.putoet.grid.Point;
import com.putoet.grid.GridUtils;

public class Virus {
    public static final char INFECTED = '#';
    public static final char CLEAN = '.';

    private Direction direction = Direction.UP;
    private int burstCount = 0;
    private int burstInfectedCount = 0;
    private int burstCleanedCount = 0;
    private Point position;

    public Virus(Point start) {
        this.position = start;
    }

    public Point position() { return position; }
    public char marker() {
        return switch (direction) {
            case UP -> '^';
            case RIGHT -> '>';
            case DOWN -> 'v';
            case LEFT -> '<';
        };
    }

    public Direction direction() { return direction; }
    public int burstCount() { return burstCount; }
    public int burstInfectedCount() { return burstInfectedCount; }
    public int burstCleanedCount() { return burstCleanedCount; }

    public char[][] burst(char[][] grid) {
        grid = checkPosition(grid);

        direction = direction(currentNode(grid));
        affectCurrentPosition(grid);
        moveForward();

        burstCount++;

        return grid;
    }

    protected void affectCurrentPosition(char[][] grid) {
        if (currentNode(grid) == INFECTED)
            clean(grid);
        else
            infect(grid);
    }

    protected void infect(char[][] grid) {
        currentNode(grid, INFECTED);
        burstInfectedCount++;
    }

    protected void clean(char[][] grid) {
        currentNode(grid, CLEAN);
        burstCleanedCount++;
    }

    protected Direction direction(char state) {
        return state == INFECTED ? direction.turnRight() : direction.turnLeft();
    }

    private char[][] checkPosition(char[][] grid) {
        if ((position.x() < 0 || position.x() >= grid.length) || (position.y() < 0 || position.y() >= grid.length)) {
            position = Point.of(position.x() + grid.length, position.y() + grid.length);
            grid = GridUtils.grow(grid, CLEAN);
        }

        return grid;
    }

    private void moveForward() {
        position = switch (direction) {
            case UP -> Point.of(position.x(), position.y() - 1);
            case RIGHT -> Point.of(position.x ()+ 1, position.y());
            case DOWN -> Point.of(position.x(), position.y() + 1);
            case LEFT -> Point.of(position.x() - 1, position.y());
        };
    }

    public char currentNode(char[][] grid) {
        return grid[position.y()][position.x()];
    }

    public void currentNode(char[][] grid, char state) {
        grid[position.y()][position.x()] = state;
    }
}
