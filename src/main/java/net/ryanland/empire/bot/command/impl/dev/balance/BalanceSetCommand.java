package net.ryanland.empire.bot.command.impl.dev.balance;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.arguments.Enum.Balance;
import net.ryanland.empire.bot.command.arguments.number.IntegerArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.permissions.Permission;

public class BalanceSetCommand extends Command {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("set")
            .description("Sets the provided balance to the provided value.")
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
            .setDescription(String.format("Successfully set %s's %s balance to %s.",
                user.getAsMention(), balance, value))
            .addLogo()
        );
    }
}
