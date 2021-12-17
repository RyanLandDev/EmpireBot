package net.ryanland.empire.bot.command.impl.dev.balance;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.arguments.Enum.Balance;
import net.ryanland.empire.bot.command.arguments.number.IntegerArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.builders.PresetType;

public class BalanceSubtractCommand extends Command {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("subtract")
            .description("Subtracts the provided value from the provided balance.")
            .permission(Permission.DEVELOPER)
            ;
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new EnumArgument<>(Balance.class)
                .description("Balance to modify.")
                .id("balance"),
            new IntegerArgument()
                .description("Value to subtract.")
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

        int currentValue = balance.getGetter().apply(document);
        balance.getSetter().apply(document, currentValue - value);
        document.update();

        event.reply(new PresetBuilder(PresetType.SUCCESS)
            .setDescription(String.format("Successfully subtracted %s from %s' %s balance.",
                value, user.getAsMention(), balance))
            .addLogo()
        );
    }
}
