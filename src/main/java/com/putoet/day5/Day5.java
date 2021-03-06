package com.putoet.day5;

import com.putoet.resources.ResourceLines;

public class Day5 {
    public static void main(String[] args) {
        Jumper jumper = new Jumper(ResourceLines.intList("/day5.txt"));
        System.out.println("Number of steps to exit the list (increasing) is " + jumper.run());

        jumper = new Jumper(ResourceLines.intList("/day5.txt"), true);
        System.out.println("Number of steps to exit the list (decreasing) is " + jumper.run());
    }
}
