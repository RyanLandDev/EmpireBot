package net.ryanland.empire.bot.command.impl.gameplay.combat;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.gameplay.combat.troop.Troop;
import net.ryanland.empire.sys.gameplay.combat.wave.Wave;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

import java.util.ArrayList;
import java.util.List;

public class WaveCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("wave")
            .description("View information about the current wave.")
            .category(Category.COMBAT)
            .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        int wave = event.getProfile().getWave();
        Troop[] troops = Wave.getTroops(wave);

        event.reply(new PresetBuilder(
            """
                Below is a list of all the troops you can expect in this wave.
                You can start this wave using `/newwave`.
                **Note:** These are the troops that are __guaranteed__ to come. There may be more.
                \u200b""",
            "Wave " + wave + " Info"
        )
            .addField("🤺 Troops", genTroopsOverview(troops) + "\n\u200b"));
    }

    private static String genTroopsOverview(Troop[] troops) {
        List<String> overview = new ArrayList<>();

        for (Troop troop : troops) {
            overview.add("%s %s %s".formatted(
                troop.getQuantity() == 1 ? AIR : "*" + troop.getQuantity() + "x* \u200b",
                troop.getEmoji(),
                troop.getName()
            ));
        }

        return String.join("\n", overview);
    }
}
