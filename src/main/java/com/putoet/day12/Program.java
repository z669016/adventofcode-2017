package com.putoet.day12;

public record Program(String name) {
    @Override
    public String toString() {
        return name;
    }
}
