package com.putoet.day11;

enum Direction {
    NORTH,
    NORTH_EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    NORTH_WEST;

    static Direction of(String direction) {
        return switch (direction) {
            case "n" -> NORTH;
            case "ne" -> NORTH_EAST;
            case "se" -> SOUTH_EAST;
            case "s" -> SOUTH;
            case "sw" -> SOUTH_WEST;
            case "nw" -> NORTH_WEST;
            default -> throw new IllegalArgumentException("Invalid direction '" + direction + "'");
        };
    }
}