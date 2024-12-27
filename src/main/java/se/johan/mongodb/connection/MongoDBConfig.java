package se.johan.mongodb.connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConfig {

    //Connection URI
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";

    //Databasnamn
    private static final String DATABASE_NAME = "minDatabas";

    //Privat MongoClient-instans (Singleton-mönster)
    private static MongoClient mongoClient = null;

    /**
     * Metod för att hämta en MongoDB-databasanslutning.
     *
     * @return MongoDatabase-instans
     */

    public static MongoDatabase getConnection() {
        if (mongoClient == null) {
            //Skapa en mongoClient om det inte finns redan
            mongoClient = MongoClients.create(CONNECTION_STRING);
        }

        //Returnera databas
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    /**
     * Stäng anslutningen till MongoDB
     */
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }


}
