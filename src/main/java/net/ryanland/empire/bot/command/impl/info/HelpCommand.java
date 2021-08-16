package net.ryanland.empire.bot.command.impl.info;

import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.executor.CommandHandler;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.CommandArgument;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.info.HelpMaker;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.interactions.menu.tab.TabMenuBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HelpCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("help")
                .aliases("commands", "command")
                .description("Get a list of all commands or information about a specific one.")
                .category(Category.INFORMATION)
                .cooldown(3);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new CommandArgument()
                    .id("command")
                    .optional()
                    .description("Command to get information of")
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
        // Init menu
        TabMenuBuilder menu = new TabMenuBuilder();

        // Add home to menu
        PresetBuilder homePage = new PresetBuilder(
                "Use the buttons below to navigate through all command categories.\n" +
                        "You can get help for a specific command using "+HelpMaker.formattedUsageCode(event)
                                + ".\n\nBot made by "+ Empire.RYANLAND)
                .addLogo();
        menu.addPage("Home", homePage.builder(), true);

        // Iterate over all command categories
        for (Category category : Category.getCategories()) {
            // Get all commands, and filter by category equal and player has sufficient permissions
            List<Command> commands = CommandHandler.getCommands().stream().filter(c ->
                    c.getCategory() == category &&
                    c.getPermission().hasPermission(event.getMember())
            ).collect(Collectors.toList());

            // If no commands were left after the filter, do not include this category in the menu
            if (commands.size() == 0) continue;

            // Sort by name
            commands.sort(Comparator.comparing(Command::getName));

            // Add this category to the menu
            menu.addPage(category.getName(), new PresetBuilder(category.getDescription() +
                    "\n\n" + HelpMaker.formattedQuickCommandList(commands))
                .addLogo().builder(), category.getEmoji());
        }

        // Build and send the menu
        menu.build().send(event);
    }

    private void supplyCommandHelp(CommandEvent event, Command command) {
        event.performReply(HelpMaker.commandEmbed(event, command)).queue();
    }
}
