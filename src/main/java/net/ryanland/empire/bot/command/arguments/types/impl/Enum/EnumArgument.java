package net.ryanland.empire.bot.command.arguments.types.impl.Enum;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.types.SingleArgument;
import net.ryanland.empire.bot.events.CommandEvent;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class EnumArgument<E extends Enum<E> & EnumArgument.InputEnum> extends SingleArgument<E> {

    private EnumSet<E> associatedEnum;

    public EnumArgument<E> setEnum(Class<E> anEnum) {
        associatedEnum = EnumSet.allOf(anEnum);
        return this;
    }

    @Override
    public E parsed(OptionMapping argument, CommandEvent event) throws ArgumentException {
        for (E e : associatedEnum) {
            if (argument.getAsString().equals(e.getName())) {
                return e;
            }
        }
        throw new MalformedArgumentException("This is not a valid option! Choose from: " + getFormattedOptions());
    }

    private String getFormattedOptions() {
        return "`" +
                associatedEnum.stream()
                    .map(InputEnum::getName)
                    .collect(Collectors.joining("` `"))
                + "`";
    }

    protected interface InputEnum {

        String getName();
    }
}
