package com.putoet.day12;

import java.util.Objects;

public class Program {
    private final String name;

    private Program(String name) {
        assert name != null;

        this.name = name;
    }

    public static Program of(String name) { return new Program(name); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Program)) return false;
        Program program = (Program) o;
        return Objects.equals(name, program.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
