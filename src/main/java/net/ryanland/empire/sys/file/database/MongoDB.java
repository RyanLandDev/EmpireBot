package net.ryanland.empire.sys.file.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import net.ryanland.empire.Empire;

public class MongoDB {

    public static String DATABASE_SECRET;
    public static MongoClient MONGO_CLIENT;
    public static MongoDatabase DATABASE;

    static {
        DATABASE_SECRET = Empire.getConfig().getDatabaseURI();
        MONGO_CLIENT = MongoClients.create(DATABASE_SECRET);
        DATABASE = MONGO_CLIENT.getDatabase("EmpireBot");
    }
}
