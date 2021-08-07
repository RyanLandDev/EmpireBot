package net.ryanland.empire.bot.command.impl.dev;


import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.command.permissions.RankHandler;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class DevNamesCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData()
                .name("devnames")
                .description("Lists the current developers' names.")
                .category(Category.INFORMATION);
    }

    @Override
    public ArgumentSet getArguments() { return new ArgumentSet(); }

    @Override
    public void run(CommandEvent event) {
        // Gets a hashmap of all the user IDs with ranks
        HashMap<Long, Permission> userRanks = RankHandler.getUserRanks();
        // StringBuilder to construct the final output
        List<String> devListBuilder = new ArrayList<>();

        // Iterates through hashmap keys and adds the developers to the list
        devListBuilder.add("The current developers are;\n");
        for (Long id : userRanks.keySet()) {
            Permission value = userRanks.get(id);
            devListBuilder.add(String.format("• <@%s> %s", id, value.getName());
        }

        event.reply(
                new PresetBuilder(String.join("\n", devListBuilder)
                        )
                        .setTitle("Developers")
        );
    }
}
