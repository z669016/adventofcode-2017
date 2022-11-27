package com.putoet.day11;

import com.putoet.resources.CSV;

import java.util.List;

public class Day11 {
    public static void main(String[] args) {
        final List<Direction> directions = CSV.flatList("/day11.txt", Direction::of);
        final HexGrid endPoint = HexGrid.origin().move(directions);

        System.out.printf("End point is (%d,%d)%n", endPoint.x(), endPoint.y());
        System.out.println("Shortest path to the final cell is " + HexGrid.origin().distance(endPoint));
        System.out.println("Max distance to origin ever is " + HexGrid.origin().distance(HexGrid.max()));
    }
}
