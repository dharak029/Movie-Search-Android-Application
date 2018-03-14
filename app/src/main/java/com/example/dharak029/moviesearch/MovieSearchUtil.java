package com.example.dharak029.moviesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dharak029 on 10/16/2017.
 */

public class MovieSearchUtil {

    public static ArrayList<Movie> parseMovieSearchQuery(String inputStream) throws JSONException{

        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
        JSONObject jsonObject = new JSONObject(inputStream);
        JSONArray resultsArray = jsonObject.getJSONArray("results");

        for(int i=0;i<resultsArray.length();i++){
            JSONObject obj = resultsArray.getJSONObject(i);
            movieArrayList.add(Movie.createMovieObject(obj));
        }
        return movieArrayList;
    }
}
