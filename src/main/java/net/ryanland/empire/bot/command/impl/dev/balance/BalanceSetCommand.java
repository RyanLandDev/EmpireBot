package net.ryanland.empire.bot.command.impl.dev.balance;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.*;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;

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
                new UserArgument()
                        .description("User to modify.")
                        .id("user")
                        .optional(CommandEvent::getUser),
                new EnumArgument()
                        .description("Balance to modify.")
                        .id("balance"),
                new IntegerArgument()
                        .description("Value to set.")
                        .id("value")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        UserDocument document = DocumentCache.get(event.getArgument("user"), UserDocument.class);
        String balance = event.getArgument("balance");

        document.put(balance, (int)event.getArgument("value"));
    }
}
