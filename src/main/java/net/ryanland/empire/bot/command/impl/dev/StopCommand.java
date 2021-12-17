package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.sys.message.builders.PresetType;

public class StopCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("stop")
            .description("Stops the bot.")
            .category(Category.DEVELOPER)
            .permission(Permission.DEVELOPER);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) {
        event.reply(new PresetBuilder(PresetType.NOTIFICATION, "Bot shutting down...", "Shutdown"));
        System.exit(0);
    }
}
