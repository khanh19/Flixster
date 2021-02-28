package com.example.flixster.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.Models.Movie;
import com.example.flixster.MovieDetailActivity;
import com.example.flixster.R;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView text1;
        TextView text2;
        ConstraintLayout constraint;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            constraint = itemView.findViewById(R.id.constraint);

        }

        public void bind(Movie movie){
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10;
            text1.setText(movie.getTitle());
            text2.setText(movie.getOverView());

            String imgPath;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imgPath = movie.getBackdropPath();
            }
            else {
                imgPath = movie.getPosterPath();
            }
            Glide.with(context).load(imgPath).transform(new RoundedCornersTransformation(radius, margin)).into(imageView);

            constraint.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Movie movie1 = movies.get(position);
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie1));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, (View)text1, "title");

                context.startActivity(intent, options.toBundle());
            });


        }

    }

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "OnCreate");
        View appView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list, parent, false);
        return new ViewHolder(appView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        Log.d("MovieAdapter", "OnBind" + position);
        Movie film = movies.get(position);

        holder.bind(film);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
