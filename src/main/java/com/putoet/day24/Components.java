package com.putoet.day24;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Components {
    private final List<Component> list;

    public Components(@NotNull Components components) {
        this.list = new ArrayList<>();
        components.list.forEach(c -> list.add(new Component(c)));
    }

    private Components(List<Component> list) {
        this.list = new ArrayList<>(list);
    }

    public static Components of(@NotNull List<String> lines) {
        final var list = IntStream.range(0, lines.size()).mapToObj(id -> {
                    final var ports = lines.get(id).split("/");
                    return new Component(id, Integer.parseInt(ports[0]), Integer.parseInt(ports[1]));
                })
                .toList();

        return new Components(list);
    }

    public int size() {
        return list.size();
    }

    public List<Component> forPort(int port) {
        return list.stream().filter(c -> c.hasPort(port)).toList();
    }

    public void use(Component component) {
        list.removeIf(c -> c.equals(component));
    }
}
