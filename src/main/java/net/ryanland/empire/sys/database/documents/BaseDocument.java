package net.ryanland.empire.sys.database.documents;

import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public abstract class BaseDocument extends Document {

    public abstract void update();

    protected void checkUpdate(List<Bson> updates, int newValue, int oldValue, String key) {
        if (newValue != oldValue) updates.add(Updates.set(key, newValue));
    }

    protected void checkUpdate(List<Bson> updates, String newValue, String oldValue, String key) {
        if (!newValue.equals(oldValue)) updates.add(Updates.set(key, newValue));
    }
}
