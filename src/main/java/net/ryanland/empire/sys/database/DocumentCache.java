package net.ryanland.empire.sys.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.ryanland.empire.sys.database.documents.BaseDocument;
import net.ryanland.empire.sys.database.documents.SnowflakeDocument;
import net.ryanland.empire.sys.database.documents.impl.GlobalDocument;
import net.ryanland.empire.sys.database.documents.impl.GuildDocument;
import net.ryanland.empire.sys.database.documents.impl.UserDocument;
import org.bson.Document;

import java.util.Objects;
import java.util.WeakHashMap;

public class DocumentCache {

    // Collections
    public static MongoCollection<Document> GLOBAL_COLLECTION = MongoDB.DATABASE.getCollection("global");
    public static MongoCollection<Document> GUILD_COLLECTION = MongoDB.DATABASE.getCollection("guilds");
    public static MongoCollection<Document> USER_COLLECTION = MongoDB.DATABASE.getCollection("users");

    // Caches
    public static GlobalDocument GLOBAL_CACHE;
    public static WeakHashMap<String, GuildDocument> GUILD_CACHE = new WeakHashMap<>();
    public static WeakHashMap<String, UserDocument> USER_CACHE = new WeakHashMap<>();

    @SuppressWarnings("unchecked")
    public static <R, T extends BaseDocument> R getCache(Class<T> type) {
        if (type.isAssignableFrom(GlobalDocument.class)) {
            return (R) GLOBAL_CACHE;
        }
        if (type.isAssignableFrom(GuildDocument.class)) {
            return (R) GUILD_CACHE;
        }
        if (type.isAssignableFrom(UserDocument.class)) {
            return (R) USER_CACHE;
        }
        throw new UnsupportedOperationException();
    }

    public static <T extends BaseDocument> MongoCollection<Document> getCollection(Class<T> type) {
        if (type.isAssignableFrom(GlobalDocument.class)) {
            return GLOBAL_COLLECTION;
        }
        if (type.isAssignableFrom(GuildDocument.class)) {
            return GUILD_COLLECTION;
        }
        if (type.isAssignableFrom(UserDocument.class)) {
            return USER_COLLECTION;
        }
        throw new UnsupportedOperationException();
    }

    // --------------------------------------------

    public static GlobalDocument getGlobal() {
        return nullOr(GLOBAL_CACHE, retrieveGlobal());
    }

    public static GlobalDocument retrieveGlobal() {
        return nullOr(findGlobal(), insertGlobal());
    }

    public static GlobalDocument findGlobal() {
        Document document = GLOBAL_COLLECTION.find().first();

        if (document == null) return null;
        return new GlobalDocument(document);
    }

    public static GlobalDocument insertGlobal() {
        Document document = new Document();
        GLOBAL_COLLECTION.insertOne(document);

        cache(document, GlobalDocument.class);
        return new GlobalDocument(document);
    }

    public static <R extends BaseDocument & SnowflakeDocument, T extends ISnowflake> R get(T client, Class<R> type) {
        return get(client.getId(), type);
    }

    public static <T extends BaseDocument & SnowflakeDocument> T get(String id, Class<T> type) {
        return nullOr(getFromCache(id, type), retrieve(id, type));
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseDocument & SnowflakeDocument> T getFromCache(String id, Class<T> type) {
        return ((WeakHashMap<String, T>) getCache(type)).get(id);
    }

    public static <T extends BaseDocument & SnowflakeDocument> T retrieve(String id, Class<T> type) {
        return nullOr(find(id, type), insert(id, type));
    }

    public static <T extends BaseDocument & SnowflakeDocument> T find(String id, Class<T> type) {
        Document document = getCollection(type).find(Filters.eq("id", id)).first();
        if (document == null) return null;

        cache(document, type);
        return castDocument(document, type);
    }

    public static <T extends BaseDocument & SnowflakeDocument> T insert(String id, Class<T> type) {
        Document document = new Document("id", id);
        getCollection(type).insertOne(document);

        cache(document, type);
        return castDocument(document, type);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseDocument> T castDocument(Document document, Class<T> type) {
        if (document == null) return null;

        if (type.isAssignableFrom(GlobalDocument.class)) {
            return (T) new GlobalDocument(document);
        }
        if (type.isAssignableFrom(GuildDocument.class)) {
            return (T) new GuildDocument(document);
        }
        if (type.isAssignableFrom(UserDocument.class)) {
            return (T) new UserDocument(document);
        }

        throw new UnsupportedOperationException();
    }

    public static <T extends BaseDocument> void cache(Document document, Class<T> type) {
        castDocument(document, type).cache();
    }

    /**
     * Alternative to {@link Objects#requireNonNullElse(Object, Object)}
     */
    public static <T> T nullOr(T object, T compareTo) {
        return object != null ? object : compareTo;
    }

}
