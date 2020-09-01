package com.putoet.day1;

import java.util.List;
import com.putoet.resources.ResourceLines;

public class Day1 {
    public static void main(String[] args) {
        final List<String> lines = ResourceLines.list("/day1.txt");
        final IntProducer producer = IntProducer.of(lines.get(0));

        int sum = 0;
        while (producer.hasNext()) {
            if (producer.get() == producer.next())
                sum += producer.next();
        }

        System.out.println("Sum is " + sum);

        final IntProducer2 producer2 = IntProducer2.of(lines.get(0));
        sum = 0;
        while (producer2.hasNext()) {
            if (producer2.get() == producer2.next())
                sum += producer2.next();
        }

        System.out.println("Sum2 is " + sum);
    }
}
