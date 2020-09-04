package utilities;

import com.putoet.day10.Day10;
import org.junit.jupiter.api.Test;
import utilities.KnotHash;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnotHashTest {
    @Test
    void create() {
        final KnotHash knotHash = new KnotHash();
        final List<Integer> list = knotHash.asList();

        assertEquals(256, list.size());
        assertEquals(0, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(255, list.get(255));
    }

    @Test
    void reverse() {
        final KnotHash list = new KnotHash(new int[] {0, 1, 2, 3, 4});

        list.reverse(3);
        assertEquals(List.of(2, 1, 0, 3, 4), list.asList());

        list.reverse(4);
        assertEquals(List.of(4, 3, 0, 1, 2), list.asList());

        list.reverse(1);
        assertEquals(List.of(4, 3, 0, 1, 2), list.asList());

        list.reverse(5);
        assertEquals(List.of(3, 4, 2, 1, 0), list.asList());

        assertEquals(12, list.checksum());
    }

    @Test
    void hexadecimal() {
        assertEquals("a2582a3a0e66e6e86e3812dcb672a272", KnotHash.hash(Day10.input("")));
        assertEquals("33efeb34ea91902bb2f59c9920caa6cd", KnotHash.hash(Day10.input("AoC 2017")));
        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", KnotHash.hash(Day10.input("1,2,3")));
        assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", KnotHash.hash(Day10.input("1,2,4")));
    }
}