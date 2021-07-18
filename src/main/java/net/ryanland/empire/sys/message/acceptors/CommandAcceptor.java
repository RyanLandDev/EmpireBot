package net.ryanland.empire.sys.message.acceptors;

import net.dv8tion.jda.api.entities.Message;
import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.CommandHandler;
import net.ryanland.empire.bot.events.CommandEvent;

public class CommandAcceptor implements MessageAcceptor {

    @Override
    public boolean check(Message message) {
        if (!message.getAuthor().isBot() && (message.getContentDisplay().startsWith(Empire.getConfig().getPrefix()) ||
                message.getContentDisplay().startsWith(Empire.getJda().getSelfUser().getAsMention()))) {

            CommandHandler.run(new CommandEvent(message));

            return true;
        }

        return false;
    }
}
