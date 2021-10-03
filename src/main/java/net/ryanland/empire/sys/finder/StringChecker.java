package net.ryanland.empire.sys.finder;

public record StringChecker(String match) implements Checker<String> {

    @Override
    public CheckerResult<String> accept(String value) {
        if (value.startsWith(match)) return new CheckerResult<>(match, match);
        throw new IllegalArgumentException();
    }
}
