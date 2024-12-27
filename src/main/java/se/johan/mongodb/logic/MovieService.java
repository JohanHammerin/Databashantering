package se.johan.mongodb.logic;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;


public class MovieService {

    //en collection med document
    private final MongoCollection<Document> collection;

    // MongoDatabase injiceras via konstruktorn
    public MovieService(MongoDatabase database) {
        // Hämtar en MongoCollection för filmer
        this.collection = database.getCollection("movies");
    }

    protected void insertMovie(String title, int year) {
        Document doc = new Document("_id", new ObjectId());
        doc.append("title", title);
        doc.append("year", year);
        collection.insertOne(doc);
    }

    protected void findMovieByTitle(String title) {
        Document filter = new Document("title", title);
        Document projection = new Document("_id", false);


        FindIterable<Document> documents = collection.find(filter).projection(projection);

        if (!documents.iterator().hasNext()) {
            System.out.println("Inga filmer fanns med den titeln");
        } else {

            for (Document doc : documents) {
                System.out.println(doc.toJson());
            }
        }
    }

    protected void updateMovie(String currentTitle, String newTitle, int newYear) {
        Document filter = new Document("title", currentTitle);
        Document update = new Document("$set", new Document("title", newTitle).append("year", newYear));

        collection.updateOne(filter, update);
        System.out.println("Filmen uppdaterades: " + currentTitle + " -> " + newTitle + " (" + newYear + ")");
    }

    protected void deleteMovie(String title) {
        Document filter = new Document("title", title);
        collection.deleteOne(filter);
        System.out.println("Filmen: " + title + " har tagits bort!");
    }


    protected void findAllMovies() {
        Document projection = new Document("_id", false);

        FindIterable<Document> documents = collection.find().projection(projection);
        for (Document doc : documents) {
            System.out.println(doc.toJson());
        }
    }


    private void insertCheck(InsertOneResult result) {
        // Kontrollera insättningen
        if (result.wasAcknowledged()) {
            BsonValue insertedId = result.getInsertedId();
            System.out.println("Insatt dokument med ID: " + insertedId);
        } else {
            System.out.println("Insättning misslyckades!");
        }
    }

    private void insertCheck(InsertManyResult result) {
        if (result.wasAcknowledged()) {
            result.getInsertedIds().forEach((index, id) -> {
                System.out.println("Insatt dokument på index " + index + " med ID: " + id);
            });
        } else {
            System.out.println("Insättning misslyckades!");
        }
    }
}
