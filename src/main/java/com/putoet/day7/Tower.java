package com.putoet.day7;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Tower {
    private final Map<String, String> tower = new HashMap<>();
    private final Map<String, Integer> weight = new HashMap<>();

    public void addProgram(@NotNull String programName, int weight) {
        if (!tower.containsKey(programName))
            tower.put(programName, null);

        this.weight.put(programName, weight);
    }

    public int weight(@NotNull String programName) {
        assert weight.containsKey(programName);

        return weight.get(programName);
    }

    public int totalWeight(@NotNull String programName) {
        return weight(programName) + children(programName).stream().mapToInt(this::totalWeight).sum();
    }

    public void setParent(@NotNull String programName, @NotNull String parentName) {
        if (tower.get(programName) != null)
            throw new IllegalArgumentException("Program " + programName + " already has a parent (" + tower.get(programName) + ")");

        if (!tower.containsKey(parentName))
            throw new IllegalArgumentException("Parent " + parentName + " not part of the tower");

        tower.put(programName, parentName);
    }

    public String root() {
        return tower.entrySet().stream()
                .filter(entry -> entry.getValue() == null)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
    }

    public List<String> children(@NotNull String name) {
        return tower.entrySet().stream()
                .filter(entry -> name.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    public List<String> siblings(@NotNull String name) {
        final var parent = tower.get(name);
        if (parent == null)
            return List.of();

        return tower.entrySet().stream()
                .filter(entry -> parent.equals(entry.getValue()) && !name.equals(entry.getKey()))
                .map(Map.Entry::getKey)
                .toList();
    }

    public Optional<String> unbalancedChild(@NotNull String programName) {
        final var children = children(programName);
        final var childrenWeight = children.stream()
                .collect(Collectors.toMap(name -> name, this::totalWeight));

        final var invalidWeight = unbalancedWeight(childrenWeight);

        if (invalidWeight.isEmpty())
            return Optional.empty();

        final var unbalancedProgram = childrenWeight.entrySet().stream()
                .filter(entry -> entry.getValue().equals(invalidWeight.get()))
                .map(Map.Entry::getKey)
                .findFirst();

        if (unbalancedProgram.isPresent()) {
            final var unbalancedProgramChild = unbalancedChild(unbalancedProgram.get());
            if (unbalancedProgramChild.isPresent())
                return unbalancedProgramChild;
        }

        return unbalancedProgram;
    }

    private Optional<Integer> unbalancedWeight(Map<String, Integer> childrenWeight) {
        final var weights = childrenWeight.values().stream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        return weights.entrySet().stream()
                .filter(entry -> entry.getValue() == 1L)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private static final Pattern REGEX = Pattern.compile("([a-z]+) \\(([0-9]+)\\)");

    public static Tower of(@NotNull List<String> lines) {
        final var tower = new Tower();

        lines.forEach(line -> {
            final var program = line.split(" -> ");
            final var matcher = REGEX.matcher(program[0]);
            if (matcher.matches()) {
                final var name = matcher.group(1);
                final var weight = Integer.parseInt(matcher.group(2));

                tower.addProgram(name, weight);

                if (program.length == 2) {
                    final var children = program[1].split(", ");
                    for (var child : children) {
                        tower.setParent(child, name);
                    }
                }
            }
        });

        return tower;
    }
}
