package net.ryanland.empire.bot.command.arguments;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.Queue;
import java.util.function.Function;

public abstract class Argument<T> {

    private String name;
    private String id;
    private String description;
    private boolean optional = false;
    private Function<CommandEvent, T> optionalFunction = event -> null;

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

    public Function<CommandEvent, T> getOptionalFunction() {
        return optionalFunction;
    }

    public Argument<T> optional() {
        optional = true;
        return this;
    }

    public Argument<T> optional(Function<CommandEvent, T> defaultValue) {
        optional = true;
        optionalFunction = defaultValue;
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

    /**
     * Override this method to add argument options
     */
    public Command.Choice[] getChoices() {
        return new Command.Choice[0];
    }

    public OptionData getOptionData() {
        OptionData data = new OptionData(getType(), getName(), getDescription(), !isOptional());

        if (getChoices().length > 0) {
            data.addChoices(getChoices());
        }
        return data;
    }

    public abstract T parse(Queue<OptionMapping> arguments, CommandEvent event) throws ArgumentException;
}
