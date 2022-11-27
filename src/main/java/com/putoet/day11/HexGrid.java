package com.putoet.day11;

import java.util.*;

public record HexGrid(int x, int y) {
    private static HexGrid origin = new HexGrid(0, 0);
    private static HexGrid max = origin;

    private static HexGrid extend(int x, int y) {
        final HexGrid grid = new HexGrid(x, y);
        if (origin().distance(grid) > origin().distance(max))
            max = grid;
        return grid;
    }

    public static HexGrid origin() {
        return origin;
    }

    public static HexGrid max() {
        return max;
    }

    public static void reset() {
        origin = max = new HexGrid(0, 0);
    }

    public HexGrid move(List<Direction> directions) {
        HexGrid grid = this;
        for (Direction direction : directions)
            grid = grid.move(direction);

        return grid;
    }

    public HexGrid move(Direction direction) {
        return switch (direction) {
            case NORTH -> extend(x, y + 2);
            case NORTH_EAST -> extend(x + 1, y + 1);
            case SOUTH_EAST -> extend(x + 1, y - 1);
            case SOUTH -> extend(x, y - 2);
            case SOUTH_WEST -> extend(x - 1, y - 1);
            case NORTH_WEST -> extend(x - 1, y + 1);
        };
    }

    public int distance(HexGrid other) {
        final int distanceX = Math.abs(other.x - x);
        final int distanceY = Math.abs(other.y - y);

        return distanceX + Math.max(Math.abs(distanceY) - Math.abs(distanceX), 0) / 2;
    }
}
