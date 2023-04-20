package net.ryanland.empire.bot.command.impl.dev.balance;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.types.EnumArgument;
import net.ryanland.colossus.command.arguments.types.primitive.IntegerArgument;
import net.ryanland.colossus.command.arguments.types.snowflake.UserArgument;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.command.regular.SubCommand;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.bot.command.arguments.Enum.Balance;
import net.ryanland.empire.bot.command.impl.dev.DeveloperCommand;
import net.ryanland.empire.sys.file.database.Profile;

@CommandBuilder(
    name = "add",
    description = "Adds the provided value to the provided balance."
)
public class BalanceAddCommand extends DeveloperCommand implements SubCommand, CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new EnumArgument<>(Balance.class)
                .description("Balance to modify.")
                .name("balance"),
            new IntegerArgument()
                .description("Value to add.")
                .name("value"),
            new UserArgument()
                .description("User to modify.")
                .name("user")
                .optional(CommandEvent::getUser)
        );
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        User user = event.getArgument("user");
        Profile profile = Profile.of(event);

        Balance balance = event.getArgument("balance");
        int value = event.getArgument("value");

        int currentValue = balance.getGetter().apply(profile);
        balance.getSetter().apply(profile, currentValue + value);
        profile.update();

        event.reply(new PresetBuilder(DefaultPresetType.SUCCESS)
            .setDescription(String.format("Successfully added %s to %s's %s balance.",
                value, user.getAsMention(), balance))
            .addLogo()
        );
    }
}
