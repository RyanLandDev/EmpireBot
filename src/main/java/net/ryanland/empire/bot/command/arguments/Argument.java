package net.ryanland.empire.bot.command.arguments;

import net.ryanland.empire.bot.command.arguments.types.ArgumentType;

public class Argument<T extends ArgumentType> {

    private String name;
    private String id;
    private boolean optional;

    public Argument() {
    }

    public Argument(String name, String id) {
        this.name = name;
        this.id = id;
        this.optional = false;
    }

    public Argument(String name, String id, boolean optional) {
        this.name = name;
        this.id = id;
        this.optional = optional;
    }

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
}
