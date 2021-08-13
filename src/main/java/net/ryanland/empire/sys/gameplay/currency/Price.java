package net.ryanland.empire.sys.gameplay.currency;

public record Price<T extends Number>(Currency currency, T amount) {
}
