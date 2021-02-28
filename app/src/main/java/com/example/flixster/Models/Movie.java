package com.example.flixster.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Movie {
    public int movieId;
    public String posterPath;
    public String title;
    public String overView;
    public String backdropPath;
    Double voteAverage;
    public Movie(){

    }
    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overView = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            movies.add(new Movie(jsonArray.getJSONObject(i)));
        }

        return movies;

    }

    public String getPosterPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }
    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }
    public String getTitle(){
        return title;
    }

    public String getOverView(){
        return overView;
    }

    public Double getVoteAverage(){
        return voteAverage;
    }

    public int getMovieId(){return movieId;}
}

