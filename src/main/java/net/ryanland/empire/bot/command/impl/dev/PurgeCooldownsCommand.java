package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.data.CooldownHandler;
import net.ryanland.empire.bot.command.executor.data.Flag;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.StorageType;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class PurgeCooldownsCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("purgecooldowns")
                .description("Purges all locally active command cooldowns.")
                .category(Category.DEVELOPER)
                .flags(Flag.NO_DISABLE)
                .permission(Permission.DEVELOPER);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) {
        CooldownHandler.purgeCooldowns(StorageType.MEMORY);
        event.reply(new PresetBuilder(PresetType.SUCCESS,
                "Successfully purged all active cooldowns in memory.")).queue();
    }
}
