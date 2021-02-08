package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.Models.Movie;
import com.example.flixster.adapters.MovieAdapter;
import com.facebook.stetho.common.ArrayListAccumulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";//reason is to easily log data

    List<Movie> movies; //this will be populated within the onSuccess portion of the onCreate
    //We will be retrieving the data from the AsyncHttpClient response, and use the
    //Movie.java class to construct the object


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //attaches the XML structure to the Java file.
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList();

        // Create the adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies); //this is the activity that it's currently in, and movies is the member variable

        //set the adapter onto the recycler view
        rvMovies.setAdapter(movieAdapter);

        //set the layout manager onto the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client  = new AsyncHttpClient();    //library that helps with query separate from the main thread
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() { //this is how send the query
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) { //if query is successful
                Log.i(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;    //this will hold the json response from the database
                try {
                    JSONArray results = jsonObject.getJSONArray("results"); //returns all of the data retrieved from the database
                    Log.i(TAG, "Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));  //puts the results into the Movie class method, which will return a list of the movies.
                    movieAdapter.notifyDataSetChanged();

                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);    //if the requested data type doesn't exist
                }

            }
            @Override
            public void onFailure(int statusCode, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "onFailure");
            }


        });

    }
}