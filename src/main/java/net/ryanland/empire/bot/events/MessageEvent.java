package net.ryanland.empire.bot.events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ryanland.empire.sys.message.acceptors.CommandAcceptor;
import net.ryanland.empire.sys.message.acceptors.MessageAcceptor;
import org.jetbrains.annotations.NotNull;

public class MessageEvent extends ListenerAdapter {

    MessageAcceptor[] acceptors = new MessageAcceptor[]{
            new CommandAcceptor()
    };

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        for (MessageAcceptor acceptor : acceptors) {
            if (acceptor.check(event.getMessage())) {
                break;
            }
        }
    }

}
