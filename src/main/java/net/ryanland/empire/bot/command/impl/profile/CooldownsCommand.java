package net.ryanland.empire.bot.command.impl.profile;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.cooldown.Cooldown;
import net.ryanland.empire.bot.command.executor.cooldown.CooldownHandler;
import net.ryanland.empire.bot.command.executor.cooldown.manager.CooldownManager;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.StorageType;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.util.DateUtil;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.ryanland.empire.util.StringUtil.genTrimProofSpaces;

public class CooldownsCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("cooldowns")
            .description("Gives the time remaining on command cooldowns.")
            .category(Category.PROFILE);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        CooldownManager manager = CooldownHandler.getCooldownManager(StorageType.EXTERNAL);
        Map<String, Cooldown> cooldowns = manager.get(event.getUser()).stream()
            .collect(Collectors.toMap(cooldown -> cooldown.command().getName(), Function.identity()));

        event.reply(new PresetBuilder("Here is an overview of a couple cooldowns.\n\u200b", "Cooldowns")
            .addLogo()
            .addField("🎮 **Games**",
                genLine("Spin", "spin", 11, 19, cooldowns.get("spin")) +
                    "\u200b")
            .addField("📦 **Boxes**",
                genLine("Hourly", "claim hourly", 6, 3, cooldowns.get("hourly")) +
                    genLine("Daily", "claim daily", 9, 5, cooldowns.get("daily")) +
                    genLine("Member", "claim member", 3, 3, cooldowns.get("member")) +
                    "\u200b")
        );
    }

    private static String genLine(String title, String command,
                                  int firstSpaces, int secondSpaces,
                                  Cooldown cooldown) {
        return "%s%s - `/%s`%s - %s\n".formatted(title, genTrimProofSpaces(firstSpaces), command, genTrimProofSpaces(secondSpaces),
            cooldown == null ? "**Ready**" :
                DateUtil.formatRelative(new Date(cooldown.expires().getTime() - System.currentTimeMillis())));
    }

}
