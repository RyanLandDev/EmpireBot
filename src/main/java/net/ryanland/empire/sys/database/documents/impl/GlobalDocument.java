package net.ryanland.empire.sys.database.documents.impl;

import com.mongodb.client.model.Filters;
import net.ryanland.empire.bot.command.executor.CommandHandler;
import net.ryanland.empire.bot.command.executor.data.DisabledCommandHandler;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.documents.BaseDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GlobalDocument extends BaseDocument {

    public GlobalDocument(Document document) {
        super(document);
    }

    private List<String> disabledCommands;

    @Override
    public void updated(List<Bson> updates) {
        checkUpdate(updates, disabledCommands, getDisabledCommandsRaw(), "disabledCommands");

        performUpdate(DocumentCache.GLOBAL_COLLECTION, Filters.empty(), updates);
    }

    @Override
    public void cache() {
        DocumentCache.GLOBAL_CACHE = this;
    }

    // --------------------------------------------------------------------------

    public GlobalDocument setDisabledCommandsRaw(List<String> disabledCommands) {
        this.disabledCommands = disabledCommands;
        return this;
    }

    public GlobalDocument setDisabledCommands(List<Command> disabledCommands) {
        this.disabledCommands = DisabledCommandHandler.getInstance().serialize(disabledCommands);
        return this;
    }

    public List<String> getDisabledCommandsRaw() {
        return Objects.requireNonNullElse(getList("disabledCommands", String.class), new ArrayList<>());
    }

    public List<Command> getDisabledCommands() {
        return DisabledCommandHandler.getInstance().deserialize(getDisabledCommandsRaw());
    }

}
