package com.putoet.day12;

import org.jetbrains.annotations.NotNull;

record Program(@NotNull String name) {
    @Override
    public String toString() {
        return name;
    }
}
