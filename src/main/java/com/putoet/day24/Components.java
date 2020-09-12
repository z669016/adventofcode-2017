package com.putoet.day24;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Components {
    private final List<Component> list;

    public Components(Components components) {
        this.list = new ArrayList<>();
        components.list.forEach(c -> list.add(new Component(c)));
    }

    private Components(List<Component> list) {
        this.list = new ArrayList<>(list);
    }

    public static Components of(List<String> lines) {
        final List<Component> list = IntStream.range(0, lines.size()).mapToObj(id -> {
                    final String[] ports = lines.get(id).split("/");
                    return new Component(id, Integer.parseInt(ports[0]), Integer.parseInt(ports[1]));
                })
                .collect(Collectors.toList());

        return new Components(list);
    }

    public int size() {
        return list.size();
    }

    public List<Component> forPort(int port) {
        return list.stream().filter(c -> c.hasPort(port)).collect(Collectors.toList());
    }

    public void use(Component component) {
        list.removeIf(c -> c.equals(component));
    }
}
