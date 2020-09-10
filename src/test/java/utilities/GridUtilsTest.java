package utilities;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GridUtilsTest {

    @Test
    void of() {
        char[][] grid = GridUtils.of(List.of(
                "123",
                "456",
                "789"
        ));

        final String s = GridUtils.toString(grid);
        assertEquals("123456789", s);
    }

    @Test
    void grow() {
        char[][] grid = GridUtils.of(List.of(
                "123",
                "456",
                "789"
        ));
        grid = GridUtils.grow(grid, '.');

        final List<String> list = GridUtils.toList(grid);
        final List<String> expected = List.of(
                ".........",
                ".........",
                ".........",
                "...123...",
                "...456...",
                "...789...",
                ".........",
                ".........",
                "........."
        );
        assertEquals(expected, list);
    }
}