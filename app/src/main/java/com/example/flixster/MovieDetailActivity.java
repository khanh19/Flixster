package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.Models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailActivity extends YouTubeBaseActivity {
    private static final String YOUTUBE_API = "AIzaSyA4s52r9cRwpUorRb9xDgwPykn4vpeRoC0";
    public static final String YOUTUBE_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    Movie movie;
    TextView title;
    TextView overview;
    RatingBar rating;
    YouTubePlayerView youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_actitvity);
        //Assigning
        title = findViewById(R.id.tvTitle);
        overview = findViewById(R.id.tvOverview);
        rating = findViewById(R.id.rbVoteAverage);
        youtube = findViewById(R.id.player);
        //unwrap
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        //setting
        title.setText(movie.getTitle());
        overview.setText(movie.getOverView());
        float rat = movie.getVoteAverage().floatValue();
        rating.setRating(rat);
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(YOUTUBE_URL, movie.getMovieId()), new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int i, Headers headers, JSON json) {
                        try {
                            JSONArray results = json.jsonObject.getJSONArray("results");
                            if(results.length() == 0){
                                return;
                            }
                            String youtubeKey = results.getJSONObject(0).getString("key");
                            Log.d("MovieDetailActivity", youtubeKey);
                            initializeYoutube(youtubeKey);
                        }catch (JSONException e){
                            Log.e("MovieDetailActivity", "Failed to parse");
                        }
                    }
                    @Override
                    public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                        Log.d("MovieDetailActivity", "Fail");
                    }
                });
    }
    private void initializeYoutube(final String youtubeKey){
        youtube.initialize(YOUTUBE_API, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("MovieDetailActivity", "onInitialize Success");
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("MovieDetailActivity", "onInitialize Failure");
            }
        });
    }
}