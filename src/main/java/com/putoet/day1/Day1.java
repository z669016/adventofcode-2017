package com.putoet.day1;

import com.putoet.resources.ResourceLines;

public class Day1 {
    public static void main(String[] args) {
        final String input = ResourceLines.line("/day1.txt");

        System.out.println("Sum is " + sum(IntProducer.of(input)));
        System.out.println("Sum2 is " + sum(IntProducer2.of(input)));
    }

    private static int sum(IntProducer producer) {
        int sum = 0;
        while (producer.hasNext()) {
            if (producer.get() == producer.next())
                sum += producer.next();
        }

       return sum;
    }
}
