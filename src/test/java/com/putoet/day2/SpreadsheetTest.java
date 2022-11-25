package com.putoet.day2;

import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpreadsheetTest {
    private static final List<String> MATRIX = List.of(
            "5\t1\t9\t5",
            "7\t5\t3",
            "2\t4\t6\t8"
    );

    private Spreadsheet spreadsheet;

    @BeforeEach
    void setup() {
        spreadsheet = Spreadsheet.of(MATRIX);
    }

    @Test
    void equals() {
        assertEquals(spreadsheet, new Spreadsheet(List.of(List.of(5, 1, 9, 5), List.of(7, 5, 3), List.of(2, 4, 6, 8))));
    }

    @Test
    void min() {
        assertEquals(List.of(1, 3, 2), spreadsheet.min());
    }

    @Test
    void max() {
        assertEquals(List.of(9, 7, 8), spreadsheet.max());
    }

    @Test
    void checksum() {
        assertEquals(18, spreadsheet.checksum());
    }

    @Test
    void evenlyDividableValues() {
        final List<String> matrix = List.of(
                "5\t9\t2\t8",
                "9\t4\t7\t3",
                "3\t8\t6\t5"
        );
        final Spreadsheet spreadsheet = Spreadsheet.of(matrix);
        final List<Pair<Integer,Integer>> evenlyDividableValues = spreadsheet.evenlyDividableValues();

        assertEquals(List.of(Pair.with(8, 2), Pair.with(9, 3), Pair.with(6, 3)), evenlyDividableValues);
    }

}