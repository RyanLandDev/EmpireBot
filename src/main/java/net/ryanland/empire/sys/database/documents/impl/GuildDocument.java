package net.ryanland.empire.sys.database.documents.impl;

import com.mongodb.client.model.Filters;
import net.ryanland.empire.Empire;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.documents.BaseDocument;
import net.ryanland.empire.sys.database.documents.SnowflakeDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuildDocument extends BaseDocument implements SnowflakeDocument {

    public GuildDocument(Document document) {
        super(document);
    }

    private String prefix = getPrefix();

    @Override
    public void updated(List<Bson> updates) {
        checkUpdate(updates, prefix, getPrefix(), "prefix");

        performUpdate(DocumentCache.GUILD_COLLECTION, Filters.eq("id", getId()), updates);
    }

    @Override
    public void cache() {
        DocumentCache.GUILD_CACHE.put(getId(), this);
    }

    // ------------------------------------------------------

    public String getId() {
        return getString("id");
    }

    public GuildDocument setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getPrefix() {
        return Objects.requireNonNullElse(getString("prefix"), Empire.getConfig().getPrefix());
    }

}
