package net.ryanland.empire.bot.command.impl.gameplay.combat;

import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.combat.troop.Troop;
import net.ryanland.empire.sys.gameplay.combat.wave.Wave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CommandBuilder(
    name = "wave",
    description = "View information about the current wave."
)
public class WaveCommand extends CombatCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        int wave = Profile.of(event).getWave();
        List<Troop> troops = Wave.getTroops(wave);

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

    private static String genTroopsOverview(List<Troop> troops) {
        // Get quantities
        HashMap<String, Integer> quantities = new HashMap<>();
        for (Troop troop : troops) {
            String format = "%s %s %s".formatted(troop.getEmoji(), troop.getName(), troop.getStage());

            quantities.putIfAbsent(format, 0);
            quantities.put(format, quantities.get(format) + 1);
        }

        // Generate
        List<String> overview = new ArrayList<>();
        for (String key : quantities.keySet()) {
            int quantity = quantities.get(key);
            overview.add(
                (quantity == 1 ? AIR : "*" + quantity + "x* \u200b")
                    + " " + key
            );
        }

        return String.join("\n", overview);
    }
}
