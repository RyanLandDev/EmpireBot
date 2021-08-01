package net.ryanland.empire.sys.database.documents;

import com.mongodb.client.model.Filters;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.sys.database.MongoDB;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class GlobalDocument extends BaseDocument {

    public GlobalDocument(Document document) {
        putAll(document);
    }

    private List<Command> disabledCommands;

    public void update() {
        List<Bson> updates = new ArrayList<>();

        checkUpdate(updates, disabledCommands, getDisabledCommands(), "level");

        MongoDB.USER_DB.updateMany(Filters.eq("id", getId()), updates);
    }

    public String getId() {
        return getString("id");
    }

    public GlobalDocument setDisabledCommands(List<Command> disabledCommands) {
        this.disabledCommands = disabledCommands;
        return this;
    }

    public List<Command> getDisabledCommands() {
        return getList("disabledCommands", Command.class);
    }

}
