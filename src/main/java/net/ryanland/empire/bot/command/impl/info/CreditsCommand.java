package net.ryanland.empire.bot.command.impl.info;


import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.command.permissions.RankHandler;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CreditsCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData()
                .name("credits")
                .description("Lists the bot's credits.")
                .category(Category.INFORMATION);
    }

    @Override
    public ArgumentSet getArguments() { return new ArgumentSet(); }

    @Override
    public void run(CommandEvent event) {
        // Gets a hashmap of all the user IDs with ranks
        // DOES NOT WORK IF RankHandler'S HASHMAP IS OUT OF ORDER.
        HashMap<Long, Permission> userRanks = RankHandler.getUserRanks();
        // List to construct the final output
        List<String> devListBuilder = new ArrayList<>();

        Permission oldValue = null;
        // Iterates through hashmap keys and adds the developers to the list
        for (Long id : userRanks.keySet()) {
            Permission value = userRanks.get(id);
            if (value.equals(oldValue)) {
                devListBuilder.add(String.format("• <@%s>", id));
            } else {
                // Automatically creates a new category when a new permission level is reached.
                devListBuilder.add(String.format("\n**%s**",value.getName()));
                devListBuilder.add(String.format("• <@%s>", id));
                oldValue = value;
            }
        }

        event.reply(
                new PresetBuilder(String.join("\n", devListBuilder)
                )
                        .setTitle("Credits")
        ).queue();
    }
}
