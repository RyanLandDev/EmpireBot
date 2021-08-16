package net.ryanland.empire.bot.command.arguments;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.Queue;

public abstract class Argument<T> {

    private String name;
    private String id;
    private String description;
    private boolean optional = false;
    protected OptionType type = OptionType.STRING;

    public String getName() {
        return name == null ? id : name;
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

    public Argument<T> description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OptionType getType() {
        return type;
    }

    public abstract T parse(Queue<OptionMapping> arguments, CommandEvent event) throws ArgumentException;
}
