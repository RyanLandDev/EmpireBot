package net.ryanland.empire.sys.finder;

import net.ryanland.colossus.command.arguments.types.EnumArgument;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class EnumChecker<E extends Enum<E> & EnumArgument.InputEnum> implements Checker<E> {

    private final EnumSet<E> checks;

    public EnumChecker(Class<E> anEnum) {
        this.checks = EnumSet.allOf(anEnum);
    }

    @Override
    public CheckerResult<E> accept(String value) {
        List<E> search = checks.stream()
            .filter(e -> !e.isHidden() && value.startsWith(e.getTitle()))
            .collect(Collectors.toList());

        if (search.isEmpty()) throw new IllegalArgumentException();

        return new CheckerResult<>(search.get(0), search.get(0).getTitle());
    }
}
