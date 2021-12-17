package net.ryanland.empire.bot.command.impl.info.user;

import net.dv8tion.jda.api.entities.Member;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.CommandInfo;

public class UserNickGetSubCommand extends Command {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("get")
            .description("Get the nickname of a guild member");
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new MemberArgument()
                .description("Member to get the nickname of")
                .id("member")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Member member = event.getArgument("member");
        event.performReply(
            new PresetBuilder(
                String.format("%s's nickname: %s", member.getUser().getName(), member.getEffectiveName())
            )).queue();
    }
}
