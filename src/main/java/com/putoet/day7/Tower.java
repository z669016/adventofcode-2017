package com.putoet.day7;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Tower {
    private final Map<String,String> tower = new HashMap<>();
    private final Map<String,Integer> weight = new HashMap<>();

    public void addProgram(String programName, int weight) {
        if (!tower.containsKey(programName))
            tower.put(programName, null);

        this.weight.put(programName, weight);
    }

    public int weight(String programName) {
        assert programName != null && weight.containsKey(programName);

        return weight.get(programName);
    }

    public int totalWeight(String programName) {
        return weight(programName) + children(programName).stream().mapToInt(this::totalWeight).sum();
    }

    public void setParent(String programName, String parentName) {
        if (tower.get(programName) != null)
            throw new IllegalArgumentException("Program " + programName + " already has a parent (" + tower.get(programName) + ")");

        if (!tower.containsKey(parentName))
            throw new IllegalArgumentException("Parent " + parentName + " not part of the tower");

        tower.put(programName, parentName);
    }

    public List<String> roots() {
        return tower.entrySet().stream()
                .filter(entry -> entry.getValue() == null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> children(String name) {
        assert name != null;

        return tower.entrySet().stream()
                .filter(entry -> name.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> siblings(String name) {
        assert name != null;

        final String parent = tower.get(name);
        if (parent == null)
            return List.of();

        return tower.entrySet().stream()
                .filter(entry -> parent.equals(entry.getValue()) && !name.equals(entry.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Optional<String> unbalancedChild(String programName) {
        final List<String> children = children(programName);
        final Map<String,Integer> childrenWeight = children.stream()
                .collect(Collectors.toMap(name -> name, this::totalWeight));

        final Optional<Integer> invalidWeight = unbalancedWeight(childrenWeight);

        if (invalidWeight.isEmpty())
            return Optional.empty();

        final Optional<String> unbalancedProgram = childrenWeight.entrySet().stream()
                .filter(entry -> entry.getValue().equals(invalidWeight.get()))
                .map(Map.Entry::getKey)
                .findFirst();

        if (unbalancedProgram.isPresent()) {
            final Optional<String> unbalancedProgramChild = unbalancedChild(unbalancedProgram.get());
            if (unbalancedProgramChild.isPresent())
                return unbalancedProgramChild;
        }

        return unbalancedProgram;
    }

    private Optional<Integer> unbalancedWeight(Map<String, Integer> childrenWeight) {
        final Map<Integer,Long> weights = childrenWeight.values().stream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        final Optional<Integer> invalidWeight = weights.entrySet().stream()
                .filter(entry -> entry.getValue() == 1L)
                .map(Map.Entry::getKey)
                .findFirst();
        return invalidWeight;
    }

    private static final Pattern REGEX = Pattern.compile("([a-z]+) \\(([0-9]+)\\)");
    public static Tower of(List<String> lines) {
        final Tower tower = new Tower();

        lines.forEach(line -> {
            final String[] program = line.split(" -> ");

            final Matcher matcher = REGEX.matcher(program[0]);
            if (matcher.matches()) {
                final String name = matcher.group(1);
                final int weight = Integer.parseInt(matcher.group(2));

                tower.addProgram(name, weight);

                if (program.length == 2) {
                    final String[] children = program[1].split(", ");
                    for (String child : children) {
                        tower.setParent(child, name);
                    }
                }
            }
        });

        return tower;
    }
}
