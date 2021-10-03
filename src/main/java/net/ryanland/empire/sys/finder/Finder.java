package net.ryanland.empire.sys.finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Finder {

    private final List<Checker<?>> checkers = new ArrayList<>();

    public Finder add(Checker<?> checker) {
        checkers.add(checker);
        return this;
    }

    public Finder add(Checker<?>... checkers) {
        this.checkers.addAll(Arrays.asList(checkers));
        return this;
    }

    public List<CheckerResult<?>> execute(String input) {
        return checkers.stream()
            .map(checker -> checker.accept(input))
            .collect(Collectors.toList());
    }
}
