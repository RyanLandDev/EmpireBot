package net.ryanland.empire.bot.command.arguments;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.arguments.types.impl.MemberArgument;
import net.ryanland.empire.bot.command.arguments.types.impl.UserArgument;

public interface ArgumentPreset {

    Argument<User> USER = new UserArgument()
            .name("user")
            .id("user");

    Argument<Member> MEMBER = new MemberArgument()
            .name("member")
            .id("member");
}
