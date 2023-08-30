package com.putoet.day24;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Bridge {
    private final List<Component> list = new ArrayList<>();
    private Component last;
    private final Components components;

    public Bridge(@NotNull Component start, @NotNull Components components) {
        if (start.one() != 0)
            start.reverse();
        if (start.one() != 0)
            throw new IllegalArgumentException("Invalid start component");

        this.last = start;
        this.list.add(start);
        this.components = components;
        this.components.use(start);
    }

    private Bridge(Bridge other) {
        this.list.addAll(other.list);
        this.components = new Components(other.components);
        this.last = other.last;
    }

    private Bridge add(Component component) {
        if (component.one() != last.two()) component.reverse();

        final var b = new Bridge(this);

        b.list.add(component);
        b.components.use(component);
        b.last = component;

        return b;
    }

    public List<Bridge> options() {
        final var options = components.forPort(list.get(list.size() - 1).two());
        if (options.isEmpty())
            return List.of();

        return options.stream().map(this::add).collect(Collectors.toList());
    }

    public int strength() {
        return list.stream().mapToInt(c -> c.one() + c.two()).sum();
    }

    public int length() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.stream().map(Component::toString).collect(Collectors.joining(" -- "));
    }
}
