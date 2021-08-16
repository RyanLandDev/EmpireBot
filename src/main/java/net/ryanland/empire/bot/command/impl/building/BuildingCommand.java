package net.ryanland.empire.bot.command.impl.building;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.BuildingArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.interactions.menu.action.ActionMenu;
import net.ryanland.empire.sys.message.interactions.menu.action.ActionMenuBuilder;

public class BuildingCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("building")
                .description("Gets information about a specific building in your Empire.")
                .category(Category.BUILDING)
                .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new BuildingArgument()
                    .id("building")
                    .description("The position of the building to retrieve information from.")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Building building = event.getArgument("building");

        new ActionMenuBuilder()
                .setEmbed(new PresetBuilder("lol"))
                .addButton(Button.danger("id", "danger! xd " + building.getName()).withEmoji(Emoji.fromUnicode("😀")).asDisabled(),
                        buttonEvent -> event.getChannel().sendMessage("u clicked xd!!").queue())
                .addButton(Button.success("id2", "i am scared pls help"),
                        buttonEvent -> event.getChannel().sendMessage("u are scared xd!!").queue())
                .build()
                .send(event);

        //TODO event.reply(menu interface)
    }

}
