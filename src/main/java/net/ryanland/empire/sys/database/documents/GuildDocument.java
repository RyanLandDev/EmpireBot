package net.ryanland.empire.sys.database.documents;

import com.mongodb.client.model.Filters;
import net.ryanland.empire.sys.database.MongoDB;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class GuildDocument extends BaseDocument {

    public GuildDocument(Document document) {
        putAll(document);
    }

    private String prefix = getPrefix();

    public void update() {
        List<Bson> updates = new ArrayList<>();

        checkUpdate(updates, prefix, getPrefix(), "prefix");


        MongoDB.GUILD_DB.updateMany(Filters.eq("id", getId()), updates);
    }

    public GuildDocument setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getId() {
        return getString("id");
    }

    public String getPrefix() {
        return getString("prefix");
    }

}
