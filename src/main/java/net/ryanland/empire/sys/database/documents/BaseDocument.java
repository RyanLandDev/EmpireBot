package net.ryanland.empire.sys.database.documents;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.MongoDB;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDocument extends Document {
    public BaseDocument(Document document) {
        putAll(document);
        cache();
    }

    public abstract void updated(List<Bson> updates);

    public final void update() {
        updated(new ArrayList<>());
        cache();
    }

    public abstract void cache();

    protected final <T> void checkUpdate(List<Bson> updates, T newValue, T oldValue, String key) {
        if (!newValue.equals(oldValue)) {
            updates.add(Updates.set(key, newValue));
            put(key, newValue);
        }
    }
}
