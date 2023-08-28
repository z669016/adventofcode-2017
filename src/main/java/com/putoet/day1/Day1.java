package com.putoet.day1;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day1 {
    public static void main(String[] args) {
        final var input = ResourceLines.line("/day1.txt");

        Timer.run(() -> System.out.println("Sum is " + sum(IntProducer.of(input))));
        Timer.run(() -> System.out.println("Sum2 is " + sum(IntProducer2.of(input))));
    }

    private static int sum(IntProducer producer) {
        var sum = 0;
        while (producer.hasNext()) {
            if (producer.get() == producer.next())
                sum += producer.next();
        }

       return sum;
    }
}
