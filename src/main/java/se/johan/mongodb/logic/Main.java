package se.johan.mongodb.logic;

import se.johan.mongodb.connection.MongoDBConfig;

public class Main {
    public static void main(String[] args) {


        MovieService movieService = new MovieService(MongoDBConfig.getConnection());
        movieService.findMovieByTitle("Avengers");
        //movieService.findAllMovies();
        //movieService.updateMovie("Harry Potter", "Test", 2000);
        //movieService.deleteMovie("Avengers");

    }
}
