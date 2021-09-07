package net.ryanland.empire.bot.command.executor.functional_interface;

import net.ryanland.empire.bot.command.executor.exceptions.CommandException;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface CommandBiConsumer<T, U> extends BiConsumer<T, U> {

    @Override
    default void accept(T t, U u) {
        throw new IllegalStateException("Use CommandBiConsumer#consume instead");
    }

    void consume(T t, U u) throws CommandException;
}
