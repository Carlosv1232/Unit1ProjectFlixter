package com.example.flixster.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    String backdropPath;
    String posterPath;  //this will only return the end portion of the url path (relative path)
    String title;
    String overview;

    public Movie(JSONObject jsonObject) throws JSONException {
            posterPath = jsonObject.getString("poster_path");
            title = jsonObject.getString("title");
            overview = jsonObject.getString("overview");
            backdropPath = jsonObject.getString("backdrop_path");
    }
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i))); //calls up the constructor to add to the movie
        }

        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
        //%s is what is going to replaced with the posterPath\
        //In this example, we are hard coding everything, but what we should be doing is
        //fetching all of the available sizes, appending it to the base URL an then
        //adding in the relative path
    }

    public String getBackdropPath(){return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);}

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
