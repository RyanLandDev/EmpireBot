package net.ryanland.empire.sys.message.acceptors;

import net.dv8tion.jda.api.entities.Message;
import net.ryanland.empire.Empire;
import net.ryanland.empire.bot.command.CommandHandler;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.MongoDB;
import net.ryanland.empire.sys.database.documents.impl.GuildDocument;

public class CommandAcceptor implements MessageAcceptor {

    @Override
    public boolean check(Message message) {
        if (!message.getAuthor().isBot() &&
                (message.getContentDisplay().startsWith(DocumentCache.get(message.getGuild(), GuildDocument.class).getPrefix()) ||
                message.getContentDisplay().startsWith(Empire.getJda().getSelfUser().getAsMention()))) {

            CommandHandler.run(new CommandEvent(message));

            return true;
        }

        return false;
    }
}
