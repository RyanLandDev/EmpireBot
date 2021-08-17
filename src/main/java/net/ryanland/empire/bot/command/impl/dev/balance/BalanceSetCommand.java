package net.ryanland.empire.bot.command.impl.dev.balance;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.*;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.Balance;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.EnumArgument;
import net.ryanland.empire.bot.command.arguments.types.impl.number.IntegerArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class BalanceSetCommand extends SubCommand {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("set")
                .description("Sets the provided balance to the provided value.");
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new EnumArgument<Balance>()
                        .setEnum(Balance.class)
                        .description("Balance to modify.")
                        .id("balance"),
                new IntegerArgument()
                        .description("Value to set.")
                        .id("value"),
                new UserArgument()
                        .description("User to modify.")
                        .id("user")
                        .optional(CommandEvent::getUser)
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        User user = event.getArgument("user");
        UserDocument document = event.getUserDocument(user);

        Balance balance = event.getArgument("balance");
        int value = event.getArgument("value");

        balance.getSetter().apply(document, value);
        document.update();

        event.reply(new PresetBuilder(PresetType.SUCCESS)
                .setDescription(String.format("Successfully set %s to %s's %s balance.",
                        value, balance, user.getAsMention()))
                .addLogo()
        );
    }
}
