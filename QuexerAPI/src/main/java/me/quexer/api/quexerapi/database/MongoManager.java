package me.quexer.api.quexerapi.database;

import com.mongodb.ServerAddress;
import com.mongodb.async.client.*;
import com.mongodb.connection.ClusterSettings;
import org.bson.Document;

import java.util.Arrays;

public class MongoManager {

    private final String hostname;
    private final int port;

    private MongoClient client;
    private MongoDatabase database;

    public MongoManager(String hostname, int port, String database) {
        this.hostname = hostname;
        this.port = port;

        ClusterSettings clusterSettings = ClusterSettings.builder().hosts(Arrays.asList(new ServerAddress(hostname))).description("Local Server").build();
        MongoClientSettings settings = MongoClientSettings.builder().clusterSettings(clusterSettings).build();
        this.client = MongoClients.create(settings);
        this.database = client.getDatabase(database);
    }

    public MongoCollection<Document> getCollection(String name) {
        return getDatabase().getCollection(name);

    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
