package net.ryanland.empire.bot.command.executor.functional_interface;

import net.ryanland.empire.bot.command.executor.exceptions.CommandException;

import java.util.function.Consumer;

@FunctionalInterface
public interface CommandConsumer<T> extends Consumer<T> {

    @Override
    default void accept(T t) {
    }

    void consume(T t) throws CommandException;
}
