package net.ryanland.empire.sys.message.acceptors;

import net.dv8tion.jda.api.entities.Message;

public interface MessageAcceptor {

    boolean check(Message message);
}
