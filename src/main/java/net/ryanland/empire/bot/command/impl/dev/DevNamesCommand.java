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
    public void run(CommandEvent event) throws IOException {
        // Gets a hashmap of all the user IDs with ranks
        HashMap<Long, Permission> usersHashMap = RankHandler.getUserRanks();
        // StringBuilder to construct the final output
        StringBuilder devListBuilder = new StringBuilder();

        // Iterates through hashmap keys and adds the developers to the StringBuilder
        devListBuilder.append("The current developers are;\n");
        for (Long id : usersHashMap.keySet()) {
            devListBuilder.append("\n• <@").append(id).append("> ").append(usersHashMap.get(id).getName());
        }

        event.reply(
                new PresetBuilder(devListBuilder.toString()
                        )
                        .setTitle("Developers")
        );
    }
}
