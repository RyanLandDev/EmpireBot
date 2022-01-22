package net.ryanland.empire.bot.command.impl.profile;

import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.cooldown.Cooldown;
import net.ryanland.colossus.command.cooldown.CooldownHandler;
import net.ryanland.colossus.command.cooldown.CooldownManager;
import net.ryanland.colossus.command.cooldown.ExternalCooldownManager;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.bot.command.RequiresProfile;
import net.ryanland.empire.util.DateUtil;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.ryanland.empire.util.StringUtil.genTrimProofSpaces;

@CommandBuilder(
    name = "cooldowns",
    description = "Gives the time remaining on command cooldowns."
)
public class CooldownsCommand extends ProfileCommand implements CombinedCommand, RequiresProfile {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        CooldownManager manager = ExternalCooldownManager.getInstance();
        CooldownHandler.cleanCooldowns(manager, event.getUser());
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
