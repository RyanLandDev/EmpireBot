package net.ryanland.empire.bot.command.arguments;

import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.Queue;

public abstract class Argument<T> {

    private String name;
    private String id;
    private boolean optional = false;

    public String getName() {
        return name;
    }

    public Argument<T> name(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public Argument<T> id(String id) {
        this.id = id;
        return this;
    }

    public boolean isOptional() {
        return optional;
    }

    public Argument<T> optional() {
        this.optional = true;
        return this;
    }

    public abstract T parse(Queue<String> arguments, CommandEvent event) throws ArgumentException;
}
