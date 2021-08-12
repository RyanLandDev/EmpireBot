package net.ryanland.empire.bot.command.impl.empire;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;

public class StartCommand extends Command {
    private Object CommandArgument;

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("start")
                .description("Initialize your profile!")
                .category(Category.EMPIRE);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) {
        final User USER = event.getUser();
        /*
        TODO:
        - Check if user already has existing profile
        - Call initializeDefaults() from UserDocument
        - Error/success messages.
         */
    }
}
