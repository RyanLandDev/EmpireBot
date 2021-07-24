package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.bot.command.arguments.types.impl.CommandArgument;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.HelpMaker;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class HelpCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData()
                .name("help")
                .aliases("commands", "command")
                .description("Get a list of all commands or information about a specific one.")
                .category(Category.INFORMATION);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new CommandArgument()
                    .id("command")
                    .optional()
        );
    }

    @Override
    public void run(CommandEvent event) {
        Command command = event.getArgument("command");

        if (command == null) {
            supplyCommandList(event);
        } else {
            supplyCommandHelp(event, command);
        }
    }

    private void supplyCommandList(CommandEvent event) {

    }

    private void supplyCommandHelp(CommandEvent event, Command command) {
        event.reply(HelpMaker.commandEmbed(event, command));
    }
}
