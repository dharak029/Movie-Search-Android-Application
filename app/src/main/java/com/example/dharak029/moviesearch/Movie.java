package com.example.dharak029.moviesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dharak029 on 10/16/2017.
 */

public class Movie implements Serializable{

    String movieName,overview,releaseDate,posterPath;
    double rating,popularity;

    public static Movie createMovieObject(JSONObject js) throws JSONException {

        Movie movie = new Movie();
        movie.setMovieName(js.getString("original_title"));
        movie.setOverview(js.getString("overview"));
        movie.setPopularity(js.getDouble("popularity"));
        movie.setPosterPath(js.getString("poster_path"));
        movie.setRating(js.getDouble("vote_average"));
        movie.setReleaseDate(js.getString("release_date"));
        return movie;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
