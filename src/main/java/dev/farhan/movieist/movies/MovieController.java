package dev.farhan.movieist.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
@CrossOrigin(origins = "*")
public class MovieController {
    
    @Autowired
    private MovieService service;

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies() {
        return new ResponseEntity<List<Movie>>(service.findAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId){
        return new ResponseEntity<Optional<Movie>>(service.findMovieByImdbId(imdbId), HttpStatus.OK);
    }

    // --- 🔒 NEW V2 AUTHENTICATED ENDPOINTS ---

    @GetMapping("/trailers")
    public ResponseEntity<List<Movie>> getTrailers() {
        // Public route: Anyone can fetch the movies to see the trailers
        return new ResponseEntity<List<Movie>>(service.findAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/full")
    public ResponseEntity<List<Movie>> getFullMovies() {
        // Protected route: Spring Security ensures only users with a valid JWT get in here
        return new ResponseEntity<List<Movie>>(service.findAllMovies(), HttpStatus.OK);
    }
}