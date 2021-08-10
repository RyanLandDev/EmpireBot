package net.ryanland.empire.bot.command.impl.info.UserSubCommands;

import net.dv8tion.jda.api.entities.Member;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.MemberArgument;
import net.ryanland.empire.bot.command.executor.CommandException;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

public class NickGetCommand extends SubCommand {
    @Override
    public CommandData getData() {
        return new CommandData()
                .name("get")
                .description("Get the nickname of a guild member");
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new MemberArgument().name("member").description("Member to get the nickname of").id("member")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        event.reply(new PresetBuilder(((Member) event.getArgument("member")).getUser().getName() + "'s nickname: " + ((Member) event.getArgument("member")).getEffectiveName())).queue();
    }
}
