package net.ryanland.empire.bot.command.impl.info.user;

import net.dv8tion.jda.api.entities.Member;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.EndlessStringArgument;
import net.ryanland.empire.bot.command.arguments.types.impl.MemberArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.impl.SubCommand;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

public class UserNickSetSubCommand extends SubCommand {
    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("set")
                .description("Set the name of a guild member")
                .permission(Permission.SERVER_ADMIN);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new MemberArgument().name("member").id("member").description("The affected member"),
                new EndlessStringArgument().name("nickname").id("nick").description("That nickname will be applied to the given member")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Member member = event.getArgument("member");
        String newNick = event.getArgument("nick");
        member.modifyNickname(newNick).queue();
        event.reply(new PresetBuilder(PresetType.SUCCESS, "Successfully changed " + member.getUser().getName() + "'s nickname to " + newNick)).queue();
    }
}
