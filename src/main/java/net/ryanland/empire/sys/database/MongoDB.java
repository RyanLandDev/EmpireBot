package net.ryanland.empire.sys.database;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.Empire;
import net.ryanland.empire.sys.database.documents.GlobalDocument;
import net.ryanland.empire.sys.database.documents.GuildDocument;
import net.ryanland.empire.sys.database.documents.UserDocument;
import org.bson.Document;

public class MongoDB {

    public static String DATABASE_SECRET;
    public static MongoClient MONGO_CLIENT;
    public static MongoDatabase DATABASE;

    public static MongoCollection<Document> GLOBAL_DB;
    public static MongoCollection<Document> GUILD_DB;
    public static MongoCollection<Document> USER_DB;

    public static void initialize() {
        DATABASE_SECRET = Empire.getConfig().getDatabaseURI();
        MONGO_CLIENT = MongoClients.create(DATABASE_SECRET);
        DATABASE = MONGO_CLIENT.getDatabase("EmpireBot");

        GLOBAL_DB = DATABASE.getCollection("global");
        GUILD_DB = DATABASE.getCollection("guilds");
        USER_DB = DATABASE.getCollection("users");
    }

    public static UserDocument getUserDocument(User user) {
        return getUserDocument(user.getId());
    }

    public static UserDocument getUserDocument(String id) {
        UserDocument document = findUserDocument(id);
        if (document == null) {
            USER_DB.insertOne(new Document("id", id));
            document = findUserDocument(id);
        }

        return document;
    }

    private static UserDocument findUserDocument(String id) {
        FindIterable<Document> iterDoc = USER_DB.find(Filters.eq("id", id));
        for (Document document : iterDoc) {
            return new UserDocument(document);
        }
        return null;
    }

    public static GuildDocument getGuildDocument(Guild guild) {
        return getGuildDocument(guild.getId());
    }

    public static GuildDocument getGuildDocument(String id) {
        GuildDocument document = findGuildDocument(id);
        if (document == null) {
            GUILD_DB.insertOne(new Document("id", id));
            document = findGuildDocument(id);
        }

        return document;
    }

    private static GuildDocument findGuildDocument(String id) {
        FindIterable<Document> iterDoc = GUILD_DB.find(Filters.eq("id", id));
        for (Document document : iterDoc) {
            return new GuildDocument(document);
        }
        return null;
    }

    public static GlobalDocument getGlobalDocument() {
        GlobalDocument document = findGlobalDocument();
        if (document == null) {
            GLOBAL_DB.insertOne(new Document());
            document = findGlobalDocument();
        }

        return document;
    }

    private static GlobalDocument findGlobalDocument() {
        FindIterable<Document> iterDoc = GUILD_DB.find();
        for (Document document : iterDoc) {
            return new GlobalDocument(document);
        }
        return null;
    }
}
